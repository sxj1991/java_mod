package com.lazzy.base.design_patterns.templateMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * ftp 方式连接服务器
 */
@Slf4j
public class FtpConnectServer extends AbstractConnectServer{
    public FtpConnectServer(String userName, String password) {
        super(userName, password);
    }

    @Override
    protected void login(String userName, String password) {
        log.info("ftp连接服务器{}-{}",userName,password);
    }

    @Override
    protected void logout() {
        log.info("ftp退出服务器");
    }
}
