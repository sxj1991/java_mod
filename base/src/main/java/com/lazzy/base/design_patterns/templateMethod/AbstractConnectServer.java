package com.lazzy.base.design_patterns.templateMethod;


import lombok.extern.slf4j.Slf4j;


/**
 * 模板方法模式
 * 模板抽象类 定义连接服务器--根据指令进行操作
 */
@Slf4j
public abstract class AbstractConnectServer {
    /**
     * 模板方法--指定流程
     */
    public void toServerCommand(String command){
        login();
        transToServer(command);
        readFromServer();
        logout();
    }

    /**
     * 连接远程终端
     */
    protected abstract void login();

    /**
     * 推出远程终端
     */
    protected abstract void logout();

    /**
     * 将消息转发到终端
     */
    private void transToServer(String command) {
        log.info("终端接收指令："+command);
    }

    /**
     * 读取服务器响应数据
     */
    private void readFromServer(){
      log.info("终端响应消息");
    }

}
