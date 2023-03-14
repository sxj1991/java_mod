package com.lazzy.base.web.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
@Component(value ="auth")
public class Authentication {
    public boolean permission(String value){
        return "system:permission:add".equals(value);
    }
}
