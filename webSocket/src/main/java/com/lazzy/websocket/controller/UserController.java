package com.lazzy.websocket.controller;



import com.lazzy.websocket.entity.User;
import com.lazzy.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 13:22 2019/4/15
 * @ Description：在本行按ctrl + alt + v 自动填充变量
 * @ Modified By：
 * @Version: $
 */
@Controller
@RequestMapping(value={"/user"})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/selectUser.do")
    @ResponseBody
    public List<User> selectUser(){
        userService.selectUser().size();
        return userService.selectUser();
    }

    @RequestMapping(value = "/saveUser.do")
    @ResponseBody
    public Boolean saveUser( User user) throws IOException {
        WebSocketSet.sendInfo("张三","加入聊天");
        return userService.saveUser(user);
    }






}