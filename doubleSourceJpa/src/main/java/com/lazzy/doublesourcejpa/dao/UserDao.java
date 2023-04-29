package com.lazzy.doublesourcejpa.dao;


import com.lazzy.doublesourcejpa.annotation.DataSource;
import com.lazzy.doublesourcejpa.entity.User;
import com.lazzy.doublesourcejpa.enums.DataSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @Query(value = "select * from tb_user", nativeQuery = true)
    List<User> findUser();

    @DataSource(value = DataSourceType.MASTER)
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @Query(value = "select * from tb_user", nativeQuery = true)
    List<User> findUserMaster();
}
