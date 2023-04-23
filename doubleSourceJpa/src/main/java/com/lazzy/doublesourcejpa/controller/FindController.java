package com.lazzy.doublesourcejpa.controller;


import com.lazzy.doublesourcejpa.annotation.DataSource;
import com.lazzy.doublesourcejpa.dao.NoteDao;
import com.lazzy.doublesourcejpa.dao.UserDao;
import com.lazzy.doublesourcejpa.entity.Note;
import com.lazzy.doublesourcejpa.entity.User;
import com.lazzy.doublesourcejpa.enums.DataSourceType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class FindController {
    @Resource
    private UserDao userDao;

    @Resource
    private NoteDao noteDao;

    @DataSource(value = DataSourceType.SLAVE)
    @GetMapping("u")
    public List<User> findUsers(){
        return userDao.findAll();
    }


    @GetMapping("n")
    public List<User> findNotes(){
        return userDao.findAll();
    }
}
