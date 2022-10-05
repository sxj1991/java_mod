package com.lazzy.websocket.example;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * WSEndPoint：一个单独且完整的ws连接
 * 这整个WSEndPoint都被放在连接池里维护
 */
@ServerEndpoint(value = "/web/{token}")
@Component
public class WSEndpoint {

    private String token; //当前ws连接的标识
    private Session session; //当前ws会话

    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) throws IOException {

        this.session = session;
        this.token = token;
        WSPool.addWsEndPoint(token, this);
        session.getBasicRemote().sendText("与服务器建立ws连接成功，当前服务器连接池大小："+WSPool.getSize());
        System.out.println("与客户端"+token+"建立ws连接，当前服务器连接池大小："+WSPool.getSize());

    }

    @OnClose
    public void onClose() throws IOException {

        this.session.close();
        WSPool.removeWsEndPoint(token);
        System.out.println("websocket连接关闭："+token);

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        System.out.println("收到用户消息："+this.token + "：" + message);
        session.getBasicRemote().sendText("服务器已收到你的消息。");

    }

    @OnError
    public void onError(Session session, Throwable e) throws IOException {

        session.close();
        WSPool.removeWsEndPoint(token);
        System.out.println("websocket连接错误："+this.token);

    }


    /* getter and setter */

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}