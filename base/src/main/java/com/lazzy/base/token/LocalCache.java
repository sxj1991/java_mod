package com.lazzy.base.token;

import org.springframework.stereotype.Component;
 
import java.util.concurrent.ConcurrentHashMap;

/**
 * 公用本地缓存
 */
public class LocalCache {

    private static ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    private LocalCache(){

    }

    public static boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    /**
     * 缓存值
     *
     * @param key
     * @param value
     * @author:
     */
    public static void put(String key, Object value) {
        cache.put(key, value);
    }


    /**
     * 获取值
     *
     * @param key
     * @return
     * @author:
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {

        return (T) cache.get(key);
    }
}

