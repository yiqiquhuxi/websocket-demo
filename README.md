# websocket-demo
本项目为tedu websocket demo项目


## 操作步骤
1. 客户端与服务端建立websocket链接
```
访问在线工具： http://www.easyswoole.com/wstool.html
服务器地址填入： ws://127.0.0.1:8888/socketServer/111
(111为客户端名称，可随便填写)
(注：重复以上操作既开启多个客户端)
```

2. 服务器发送消息至客户端
```
get请求：http://localhost:8888/demo/send?msg=发送客户端的消息
```
```
发送get请求至mq：(解决session共享问题)
http://localhost:8888/demo/sendMq?msg=发送客户端的消息
```
3. 查看链接数量
```
get请求：
http://localhost:8888/demo/size
```

## 项目结构如下
```
.
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── websocketdemo
    │   │               ├── config
    │   │               ├── consumer
    │   │               ├── controller
    │   │               ├── entity
    │   │               └── server
    │   └── resources
    └── test
        └── java
            └── com
                └── example
                    └── websocketdemo


```






