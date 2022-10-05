package com.lazzy.security.controller;


import cn.hutool.core.util.ObjectUtil;
import com.lazzy.security.cache.LocalCache;
import com.lazzy.security.entity.LoginUser;
import com.lazzy.security.entity.User;
import com.lazzy.security.token.GenToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    private AuthenticationManager authenticationManager;


    @PostMapping("login")
    public String login(@RequestBody User user){

        Authentication authenticate =
                authenticationManager.authenticate(buildAuthenticationToken(user.getUserName(),user.getPassword()));
        if(ObjectUtil.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String token = GenToken.token(loginUser.getUser().getId());
        LocalCache.setKey("token_"+loginUser.getUser().getId(),loginUser);
        return token;
    }

    @GetMapping("index")
    @PreAuthorize("@has.hasAuthority('sys:content:list')")
    public String index(){
        return "hello this is base web model";
    }

    private Authentication buildAuthenticationToken(String name, String pass){
        return new UsernamePasswordAuthenticationToken(name,pass);
    }
}
