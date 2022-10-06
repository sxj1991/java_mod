package com.lazzy.doublesourcejpa.dao;


import com.lazzy.doublesourcejpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    @Override
    List<User> findAll();
}
