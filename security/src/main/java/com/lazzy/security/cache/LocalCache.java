package com.lazzy.security.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 *  内存缓存 封装工具类
 */
public class LocalCache {

    private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);

    private static final int MAX_CACHE_SIZE = 5_0000;

    private static Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.DAYS)
            .initialCapacity(50)
            .maximumSize(MAX_CACHE_SIZE)
            .build();

    public static<T> T getKey(String key) {
        return (T) cache.getIfPresent(key);
    }

    public static<T> void setKey(String key, T value) {
        long size = cache.size();
        if (size >= MAX_CACHE_SIZE) {
            logger.warn("local cache overflow (10000.)");
        }
        cache.put(key, value);
    }

    public static void removeKey(String key) {
        cache.invalidate(key);
    }

    public static void clearAll() {
        cache.invalidateAll();
    }

    public static ConcurrentMap<String, Object> queryAll() {
        return cache.asMap();
    }
}