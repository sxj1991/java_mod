package com.lazzy.base.message_sensitive;

import com.lazzy.base.se.generics.ResultDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
public class UserController {
    @GetMapping("/u")
    public ResultDto<User> getUsers() {
        User user = new User();
        buildUser(user);
        return  buildResult(user);
    }

    private ResultDto<User> buildResult(User user){
        ResultDto<User> userResultDto = new ResultDto<>();
        userResultDto.setData(user);
        return  userResultDto;
    }

    private void buildUser(User user){
        user.setCard("500100202201189113");
        user.setPhone("18812366627");
        user.setName("zhangsan");
        user.setPassword("aqazwsx12309887@");
    }
}
