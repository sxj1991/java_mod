package com.example.mybatis.dao;



import com.example.mybatis.annotation.DataSource;
import com.example.mybatis.entity.User;
import com.example.mybatis.enums.DataSourceType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    @DataSource(value = DataSourceType.SLAVE)
    List<User> findUser();

    @DataSource(value = DataSourceType.MASTER)
    List<User> findUserMaster();
}
