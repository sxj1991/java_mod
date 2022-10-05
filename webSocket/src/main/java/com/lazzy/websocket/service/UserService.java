package com.lazzy.websocket.service;




import com.lazzy.websocket.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 13:22 2019/4/15
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Service
public class UserService {

    private final List<User> users =new ArrayList<>(100);

    /**
     * 注入mapper
     */

    public List<User> selectUser(){
        return users;
    }

    public boolean saveUser(User user){
        return users.add(user);
    }


}