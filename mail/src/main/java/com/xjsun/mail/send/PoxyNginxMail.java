package com.xjsun.mail.send;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class PoxyNginxMail {
    public static void main(String[] args) throws MessagingException, GeneralSecurityException {
        //设置代理服务器
        Properties props = System.getProperties();
        // 设置邮件服务器代理主机名
        props.setProperty("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");

        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getInstance(props);

        Message message = new MimeMessage(session);
        Address address = new InternetAddress("xxx@163.com");

        message.setFrom(address);
        message.setSubject("nginx代理服务器转发邮件测试");
        message.setText("你好，网易邮箱！");
        message.setSentDate(new Date());

        Transport transport = session.getTransport();
        //邮件服务器、发送邮箱账号、第三方验证码
        transport.connect("xxx@163.com", "123");
        transport.sendMessage(message, new Address[] { new InternetAddress("xxx@163.com") });
        transport.close();
        log.info("邮件发送！");


    }

}
