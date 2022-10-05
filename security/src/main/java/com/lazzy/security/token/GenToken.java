package com.lazzy.security.token;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lazzy.security.exception.BaseException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token 工具类 用来生成和解析token
 */
public class GenToken {
    //设置过期时间
    private static final long EXPIRE_DATE = 30 * 60 * 100000;
    //token秘钥
    private static final String TOKEN_SECRET = "ZCEQIUBFKSJBFJH2020BQWE";

    public static String token(String userId) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
        //秘钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头部信息
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //携带username，password信息，生成签名
        return JWT.create()
                .withHeader(header)
                .withClaim("id", userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static String verify(String token){
        /**
         * @desc   验证token，通过返回true
         * @create 2019/1/18/018 9:39
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("id").asString();
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("登录验证失败");
        }
    }
}
