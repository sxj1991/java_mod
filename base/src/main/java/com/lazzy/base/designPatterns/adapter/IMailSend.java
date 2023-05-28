package com.lazzy.base.designPatterns.adapter;

/**
 * 需要适配的接口 邮件发送接口
 */
public interface IMailSend {
    void send(String address);
}
