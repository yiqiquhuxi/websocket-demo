package com.example.websocketdemo.server;

import com.example.websocketdemo.entity.Client;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * SocketServer
 *
 * @author chenqi
 * @date 2022/12/30 18:12
 * @description
 */
@ServerEndpoint(value = "/socketServer/{userName}")
@Component
@Slf4j
public class SocketServer {


  /**
   *
   * 用线程安全的CopyOnWriteArraySet来存放客户端连接的信息
   */
  public static CopyOnWriteArraySet<Client> socketServers = new CopyOnWriteArraySet<>();


  /**
   *
   * 用户连接时触发，我们将其添加到
   * 保存客户端连接信息的socketServers中
   *
   * @param session
   * @param userName
   */
  @OnOpen
  public void open(Session session,@PathParam(value="userName")String userName){
    socketServers.add(new Client(userName,session));
    log.info("客户端:【{}】连接成功",userName);

  }


  /**
   *
   * 连接关闭触发，通过sessionId来移除
   * socketServers中客户端连接信息
   */
  @OnClose
  public void onClose(Session session){
    socketServers.forEach(client ->{
      if (client.getSession().getId().equals(session.getId())) {
        log.info("客户端:【{}】断开连接",client.getUserName());
        socketServers.remove(client);
      }
    });
  }


  /**
   *
   * 发生错误时触发
   * @param error
   */
  @OnError
  public void onError(Throwable error,Session session) {
    socketServers.forEach(client ->{
      if (client.getSession().getId().equals(session.getId())) {
        socketServers.remove(client);
        log.error("客户端:【{}】发生异常",client.getUserName());
        error.printStackTrace();
      }
    });
  }



  /**
   *
   * 信息群发，我们要排除服务端自己不接收到推送信息
   * 所以我们在发送的时候将服务端排除掉
   * @param message
   */
  public synchronized static void sendAll(String message) {
    //群发，不能发送给服务端自己
    socketServers.parallelStream()
        .forEach(client -> {
          try {
            client.getSession().getBasicRemote().sendText(message);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    log.info("服务端推送给所有客户端 :【{}】",message);
  }



}
