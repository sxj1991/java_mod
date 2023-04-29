package com.lazzy.base.design_patterns.Iterator;

/**
 * 容器接口 提供数据的更新操作
 */
public interface Container<T> {
    void add(T t);

    void remove(T t);

    Iterator<T> iterator();
}
