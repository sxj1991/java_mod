package com.lazzy.base.design_patterns.adapter;

/**
 * 适配器模式 案例
 * 模拟-->maileSend类发送邮件的功能，phoneSend适配器调用邮件发送功能
 */
public class MailSend implements IMailSend {
    /**
     * 邮箱地址信息
     */
    public void send(String address){
        System.out.println("发送邮件地址："+address);
    }
}
