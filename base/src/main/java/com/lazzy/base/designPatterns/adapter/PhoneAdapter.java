package com.lazzy.base.designPatterns.adapter;

/**
 * 手机发送功能适配器类
 * 将一个类的接口转换成客户希望的另外一个接口
 * 适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作
 * <br>注意事项：适配器不是在详细设计时添加的，而是解决正在服役的项目的问题</br>
 * 简单来说，适配器是成型的系统为了适配新需求打的补丁，实际中不宜太多，会造成系统混乱。最好是在需求开始时考虑到多种情况
 * 框架中为了更好的适应性，使用该模式适配各种实际情况
 */
public class PhoneAdapter implements IPhoneSend{
    private MailSend mailSend;

    public PhoneAdapter(MailSend mailSend) {
        this.mailSend = mailSend;
    }

    /**
     * 通过模拟方法参数不同，通过适配器模式，调用邮件发送功能
     * @param phoneNumber 手机号码
     */
    @Override
    public void send(Integer phoneNumber) {
        System.out.println("手机适配邮件功能");
        mailSend.send(Integer.toString(phoneNumber));
    }
}
