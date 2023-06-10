package com.xjsun.mail.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthController {
    @RequestMapping(path = "auth")
    public void auth(HttpServletRequest request, HttpServletResponse response){
        log.info("nginx 访问认证");
        String user = request.getHeader("Auth-User");
        String pass = request.getHeader("Auth-Pass");
        String ip = request.getHeader("Client-Ip");
        String protocol = request.getHeader("Auth-Protocol");
        log.info("user:{}-pass:{}-ip:{}-protocol:{}",user,pass,ip,protocol);
        response.addHeader("Auth-Status","OK");
        response.addHeader("Auth-Server","smtp.163.com");
        response.addHeader("Auth-Port","25");
        response.addHeader("Auth-User", user);
        response.addHeader("Auth-Pass", pass);


    }
}
