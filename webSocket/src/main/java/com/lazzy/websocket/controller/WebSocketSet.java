package com.lazzy.websocket.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{name}")
@Component
@Slf4j
public class WebSocketSet {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Set<WebSocketSet> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String name;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("name") String name) {
        this.name=name;
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新连接加入:"+name+"！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("连接成功，当前时间：" + new java.sql.Timestamp(System.currentTimeMillis()));

            //群发消息
            for (WebSocketSet item : webSocketSet) {
                try {
                    item.sendConnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        //群发消息
        for (WebSocketSet item : webSocketSet) {
            try {
               item.sendConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/

    public void onMessage(String message, Session session,@PathParam("name") String name) {
        log.info("来自客户端"+name+"的消息:" + message);

        //群发消息
        for (WebSocketSet item : webSocketSet) {
            try {
                item.sendMessage(message);
                log.info("推送消息给:"+item.name+",消息是:"+message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     * */
     @OnError
     public void onError(Session session, Throwable error) {
             log.info("发生错误");
             error.printStackTrace();
     }


    /**
     * 服务端给客户端发送消息
     * @param message
     * @throws IOException
     */
     public void sendMessage(String message) throws IOException {
         this.session.getBasicRemote().sendText("服务端回应消息:"+message);
     }

    public void sendConnect() throws IOException {
        this.session.getBasicRemote().sendText(onlineCount+"");
    }

    /**
     * 群发自定义消息
     * */
    @OnMessage
    public static void sendInfo(@PathParam("name") String name,String message) throws IOException {
        for (WebSocketSet item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(name==null) {
                    item.sendMessage(message);
                    log.info("推送消息给:"+item.name+",消息是===》"+message);
                }else if(item.name.equals(name)){
                    item.sendMessage(message);
                    log.info("推送消息给:"+item.name+",消息是===》"+message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketSet.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketSet.onlineCount--;
    }
}