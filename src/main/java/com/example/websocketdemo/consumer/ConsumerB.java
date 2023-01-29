package com.example.websocketdemo.consumer;

import com.example.websocketdemo.server.SocketServer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ConsumerA
 *
 * @author chenqi
 * @date 2023/1/29 16:46
 * @description
 */
@Component
@RabbitListener(queues = {"2-topic-socket-queue"})
public class ConsumerB {

  @RabbitHandler
  public void getMsg(String message) {
    System.out.println("1 接收到的数据" + message);
    SocketServer.sendAll(message);
  }


}
