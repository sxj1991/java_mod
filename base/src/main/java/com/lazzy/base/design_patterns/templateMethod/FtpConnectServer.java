package com.lazzy.base.design_patterns.templateMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * ftp 方式连接服务器
 */
@Slf4j
public class FtpConnectServer extends AbstractConnectServer{
    @Override
    protected void login() {
        log.info("ftp连接服务器");
    }

    @Override
    protected void logout() {
        log.info("ftp退出服务器");
    }
}
