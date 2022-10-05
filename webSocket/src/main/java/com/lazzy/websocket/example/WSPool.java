package com.lazzy.websocket.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * websocket连接池
 * 用来维护全体ws连接：新增连接、删除连接、向某个连接发送信息等
 */
public class WSPool {

    private static Map<String, WSEndpoint> wsPool = new HashMap<>();
    private static int size = 0;

    //添加一个连接进入连接池
    public synchronized static void addWsEndPoint(String token, WSEndpoint wsed){
        WSPool.wsPool.put(token, wsed);
        ++size;
    }

    //从连接池中删除一个连接
    public synchronized static void removeWsEndPoint(String token){
        WSPool.wsPool.remove(token);
        --size;
    }

    //获取当前连接池大小
    public synchronized static int getWsPoolSize(){
        return WSPool.size;
    }

    //获取一个指定标识的ws连接
    public synchronized static WSEndpoint getWsEndPoint(String token){
        return WSPool.wsPool.get(token);
    }

    //发送信息给指定ws会话
    public synchronized static void sendMessageToOneWsEndPoint(String token, String msg){
        WSEndpoint wsed =  WSPool.wsPool.get(token);
        if(wsed != null){
            try {
                wsed.getSession().getBasicRemote().sendText(msg);
                System.out.println("向指定ws会话发送信息成功 : "+token);
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Exception: 无法向指定ws会话发送信息："+token);
            }
        }else {
            System.out.println("Exception: 不存在指定的ws会话："+token);
        }
    }


    /* getter and setter */

    public synchronized static Map<String, WSEndpoint> getWsPool() {
        return wsPool;
    }

    public synchronized static void setWsPool(Map<String, WSEndpoint> wsPool) {
        WSPool.wsPool = wsPool;
    }

    public synchronized static int getSize() {
        return size;
    }

    public synchronized static void setSize(int size) {
        WSPool.size = size;
    }
}