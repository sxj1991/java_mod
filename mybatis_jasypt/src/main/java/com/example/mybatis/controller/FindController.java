package com.example.mybatis.controller;


import com.example.mybatis.dao.UserMapper;
import com.example.mybatis.entity.User;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class FindController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("u")
    public List<User> findUsers() {
        List<User> users = users();
        users.addAll(otherUsers());
        return users;
    }

    public List<User> users() {
        return userMapper.findUser();
    }

    public List<User> otherUsers() {
        return userMapper.findUserMaster();
    }
}
