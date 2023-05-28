package com.lazzy.base.designPatterns.templateMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * sftp 方式连接服务器
 */
@Slf4j
public class SftpConnectServer extends AbstractConnectServer{


    public SftpConnectServer(String userName, String password) {
        super(userName, password);
    }

    @Override
    protected void login(String userName, String password) {
       log.info("sftp连接服务器{}-{}",userName,password);
    }

    @Override
    protected void logout() {
        log.info("sftp退出服务器");
    }
}
