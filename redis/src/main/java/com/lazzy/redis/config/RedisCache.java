package com.lazzy.redis.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 封装工具类
 */
@Component
public class RedisCache {
    @Resource
    private RedisTemplate redisTemplate;

    //String类型存储☞
    public void setString(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public Object getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    //hash类型存储值
    public void setHashOne(String key, String value) {
        redisTemplate.opsForHash().put(key, value, value);
    }

    public void setHashAll(String key, Map value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    public void getHashAll(String key) {
        List values = redisTemplate.opsForHash().values(key);
    }

    //hash类型存储值
    public void setValues(String key,String ...values) {
        String[] v = values;
        redisTemplate.opsForSet().add(key, v[0], v[1]);
    }

    public void getValues(String key) {

        Set members = redisTemplate.opsForSet().members(key);
        //迭代
        for (Object member : members) {
            //...
        }
    }



}
