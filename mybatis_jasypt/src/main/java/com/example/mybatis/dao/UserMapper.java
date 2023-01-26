package com.example.mybatis.dao;

import com.example.mybatis.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Student> queryList();
}
