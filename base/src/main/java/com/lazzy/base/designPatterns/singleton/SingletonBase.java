package com.lazzy.base.designPatterns.singleton;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Java设计模式----单例模式
 * * 核心是私有化构造方法 容器内只允许一个对象存在 广泛用于配置类 组件模块类
 */

public class SingletonBase {
    private Map<String,String> maps = new HashMap<>();

    public String getMaps(String key) {
        return maps.get(key);
    }

    public void setMaps(String key,String value) {
        maps.put(key,value);
    }

    private SingletonBase(){

    }
    private static SingletonBase singletonBase = new SingletonBase();

    //懒汉式单例模式 缺点是实现简单 如果大范围使用 内存会额外增加内存消耗 不推荐
    public static SingletonBase getSingletonBase(){
        return singletonBase;
    }

}
