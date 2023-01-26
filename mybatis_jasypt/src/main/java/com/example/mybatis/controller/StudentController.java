package com.example.mybatis.controller;

import com.example.mybatis.dao.UserMapper;
import com.example.mybatis.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stu")
public class StudentController {
    private final UserMapper userMapper;

    public StudentController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<Student> getStudent(){
        return userMapper.queryList();
    }
}
