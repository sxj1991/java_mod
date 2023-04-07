package com.lazzy.base.design_patterns.templateMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * sftp 方式连接服务器
 */
@Slf4j
public class SftpConnectServer extends AbstractConnectServer{
    @Override
    protected void login() {
       log.info("sftp连接服务器");
    }

    @Override
    protected void logout() {
        log.info("sftp退出服务器");
    }
}
