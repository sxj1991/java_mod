package com.lazzy.base.design_patterns.templateMethod;


import lombok.extern.slf4j.Slf4j;

import javax.security.sasl.AuthenticationException;


/**
 * 模板方法模式
 * 模板抽象类 定义连接服务器--根据指令进行操作
 * 另外实际中模板方法抽象类可实现接口或者继承父类实现解耦和更进一步抽象
 */
@Slf4j
public abstract class AbstractConnectServer {
    private String userName;

    private String password;

    private Boolean hasPermission = true;

    private Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public AbstractConnectServer(String userName, String password){
        this.userName = userName;
        this.password = password;
    }


    /**
     * 模板方法--指定流程 不允许子类重写该方法
     */
    public final void toServerCommand(String command){
        login(userName, password);

        // 模板方法模式 一种扩展方式：根据条件可跳过一些步骤
        if(getHasPermission()){
            transToServer(command);
            readFromServer();
        }

        logout();
    }

    /**
     * 连接远程终端
     */
    protected abstract void login(String userName, String password);

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
