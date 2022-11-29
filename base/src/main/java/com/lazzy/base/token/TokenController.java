package com.lazzy.base.token;

import com.lazzy.base.sdk.LogExcute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TokenController {

    @GetMapping("createTk")
    @LogExcute
    public JwtToken create(){
        String id = "1";
        String token = JwtToken.Token.createToken(id);
        JwtToken jwtToken = new JwtToken(new Date(), token);
        LocalCache.put("token:"+id,jwtToken);
        return jwtToken;
    }

    @GetMapping("verifyTk")
    public String verify(){
        String id = "1";
        // 获取缓存数据
        JwtToken jwtToken = LocalCache.get("token:"+id);
        String userId = JwtToken.Token.verifyToken(jwtToken.getToken());
        return userId;
    }
}
