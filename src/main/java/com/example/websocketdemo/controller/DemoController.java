package com.example.websocketdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.websocketdemo.server.SocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author chenqi
 * @date 2022/12/30 18:15
 * @description
 */
@Slf4j
@RestController
@RequestMapping("demo")
public class DemoController {

  @Autowired
  private RabbitTemplate rabbitTemplate;


  @GetMapping("send")
  public <T> T sendAll(String msg) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    try {
      SocketServer.sendAll(msg);
    } catch (Exception e) {
      log.error("socket sendAll error,{}", JSON.toJSONString(e));
      return (T) "fail";
    } finally {
      stopWatch.stop();
      log.info("socket sendAll cost,{}", stopWatch.getTotalTimeMillis());
    }
    return (T) "success";
  }


  /**
   * 发送消息至mq
   *
   * @param msg
   * @param <T>
   * @return
   */
  @GetMapping("sendMq")
  public <T> T sendMq(String msg) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    try {
      rabbitTemplate.convertAndSend("socket-fanout-exchange", null, msg);
    } catch (Exception e) {
      log.error("socket sendMq error,{}", JSON.toJSONString(e));
      return (T) "fail";
    } finally {
      stopWatch.stop();
      log.info("socket sendMq cost,{}", stopWatch.getTotalTimeMillis());
    }
    return (T) "success";
  }


  /**
   * get size
   *
   * @param <T>
   * @return
   */
  @GetMapping("size")
  public <T> String size() {
    int size = SocketServer.socketServers.size();
    return "机器A：链接长度：" + size;

  }


}
