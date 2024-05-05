package com.lazzy.mongodb.controller;

import com.lazzy.base.sdk.LogExecute;
import com.lazzy.mongodb.dao.UserDao;
import com.lazzy.mongodb.entity.Comment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("u")
    public String add(@RequestBody Comment comment){
        userDao.insertComment(comment);
        return "yes";
    }

    @PutMapping("u")
    public String update(@RequestBody Comment comment){
        userDao.updateComment(comment);
        return "yes";
    }

    @GetMapping("u")
    @LogExecute
    public List<Comment> query(){
        return userDao.findAll();
    }
}
