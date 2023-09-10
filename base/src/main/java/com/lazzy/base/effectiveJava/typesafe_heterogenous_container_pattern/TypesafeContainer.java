package com.lazzy.base.effectiveJava.typesafe_heterogenous_container_pattern;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 优先考虑类型安全的异构容器
 * 非异构容器：容器内部存储的对象均为同一类型
 * 异构容器：为了更多灵活性，容器内部存储对象非同一类型
 * 异构容器问题：java泛型特性存储时会抹除类型，当容器内部对象非同一类型，需要做很多额外校验且会出现运行时异常。
 */
public class TypesafeContainer {
    /**
     * 解决方案：对map容器进一步封装，构建类型安全的异构容器，class类作为key，存储该类型对象
     */
    private TypesafeContainer() {
    }

    private static final Map<Class<?>, Object> container = new ConcurrentHashMap<>();

    public static <T> void putInstance(Class<T> tClass, T instance) {
        Objects.requireNonNull(instance);
        // 如果需要避免重复 put
        if (container.containsKey(tClass)) {
            throw new RuntimeException("实例已被管理");
        }
        container.put(tClass, instance);
    }

    public static <T> T getInstance(Class<T> tClass) {
        return tClass.cast(container.get(tClass));
    }

}
