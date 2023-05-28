package com.lazzy.base.auth.service;

import org.springframework.stereotype.Component;
@Component(value ="auth")
public class Authentication {
    public boolean permission(String value){
        return "system:permission:add".equals(value);
    }
}
