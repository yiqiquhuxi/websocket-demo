package com.example.websocketdemo.entity;

import java.io.Serializable;
import javax.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Client
 *
 * @author chenqi
 * @date 2022/12/30 18:13
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

  private String userName;

  private Session session;

}
