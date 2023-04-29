package com.lazzy.doublesourcejpa.controller;


import com.lazzy.doublesourcejpa.annotation.DataSource;
import com.lazzy.doublesourcejpa.dao.NoteDao;
import com.lazzy.doublesourcejpa.dao.UserDao;
import com.lazzy.doublesourcejpa.datasource.DynamicDataSourceContextHolder;
import com.lazzy.doublesourcejpa.entity.Note;
import com.lazzy.doublesourcejpa.entity.User;
import com.lazzy.doublesourcejpa.enums.DataSourceType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试双数据源数据切换查询
 * jpa框架发现bug：同一请求中，数据源无法切换
 * 原因：JPA方法执行完成之后，并没有释放数据库连接。
 *      AbstractRoutingDataSource重写的方法determineCurrentLookupKey不会执行
 * 解决办法：可以在切面中，注入entityManage断开session或者在配置文件中open-in-view: false 都会让jpa重新获取连接
 */
@RestController
public class FindController {
    @Resource
    private UserDao userDao;
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("u")
    public List<User> findUsers() {
        List<User> users = users();
        // 检测spring.jpa.open-in-view: false 配置是否影响事务
        // if(!ObjectUtils.isEmpty(user)){
        //    throw new RuntimeException("测试事务是否成功!");
        // }
        users.addAll(otherUsers());
        return users;
    }

    public List<User> users() {
        return userDao.findUser();
    }

    public List<User> otherUsers() {
        return userDao.findUserMaster();
    }
}
