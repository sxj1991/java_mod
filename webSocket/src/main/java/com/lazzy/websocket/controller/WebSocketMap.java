package com.lazzy.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocketMap/{name}")
@Component
@Slf4j
public class WebSocketMap {


    //concurrent包的线程安全Map，用来存放每个客户端对应的WebSocket对象。
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private static Map<String,Session> webSocketMap = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("name") String name) {
        //加入map中
        webSocketMap.put(name,session);
        log.info("有新连接加入:"+name+"！当前在线人数为" + webSocketMap.size());
        try {
            sendMessage(name,"连接成功，当前时间：" + new java.sql.Timestamp(System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("name") String name) throws IOException {
        //从Map中删除
        webSocketMap.remove(name);
        log.info("有一连接关闭！当前在线人数为" + webSocketMap.size());
    }

    public void sendMessage(String name,String message) throws IOException {
        Session session = webSocketMap.get(name);
        session.getBasicRemote().sendText("服务端回应消息:"+message);
    }
}
