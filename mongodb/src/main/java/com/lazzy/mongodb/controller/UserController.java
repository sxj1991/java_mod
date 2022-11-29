package com.lazzy.mongodb.controller;

import com.lazzy.base.sdk.LogExcute;
import com.lazzy.mongodb.dao.UserDao;
import com.lazzy.mongodb.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("u")
    public String add(@RequestBody Student stu){
        userDao.insertStudent(stu);
        return "yes";
    }

    @PutMapping("u")
    public String update(@RequestBody Student stu){
        userDao.updateStudent(stu);
        return "yes";
    }

    @GetMapping("u")
    @LogExcute
    public List<Student> query(){

        return userDao.findAll();
    }
}
