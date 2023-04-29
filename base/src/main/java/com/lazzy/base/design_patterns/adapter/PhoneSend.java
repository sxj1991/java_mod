package com.lazzy.base.design_patterns.adapter;

/**
 * 手机发送信息 普通类
 */
public class PhoneSend implements IPhoneSend{
    private PhoneAdapter phoneAdapter;

    public PhoneSend(PhoneAdapter phoneAdapter) {
        this.phoneAdapter = phoneAdapter;
    }

    @Override
    public void send(Integer phoneNumber) {
        System.out.println("手机发送信息");
    }

}
