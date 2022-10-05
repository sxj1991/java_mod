package com.lazzy.security.service;



import com.lazzy.security.entity.LoginUser;
import com.lazzy.security.entity.User;
import com.lazzy.security.exception.BaseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class CacheUserDetails implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    public CacheUserDetails(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = buildCacheUser();
        if(!username.equals(user.getUserName())){
            throw new BaseException("用户名或密码错误");
        }
        //封装成UserDetails对象返回
        //设置权限 (正常业务均是从数据库获取 此处简化)
        List<String> list = new ArrayList<>(Arrays.asList("sys:content:list"));
        return new LoginUser(user,list);
    }

    private User buildCacheUser(){
        //密码加密
        return new User("1","man","21",passwordEncoder.encode("123"));
    }
}
