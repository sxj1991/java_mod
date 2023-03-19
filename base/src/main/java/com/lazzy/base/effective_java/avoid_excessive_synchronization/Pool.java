package com.lazzy.base.effective_java.avoid_excessive_synchronization;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * effective java 并发章节
 * 介绍：过度使用同步操作会带来严重的性能问题，甚至造成死锁等一系列奇怪bug
 */
public class Pool {
    private final LinkedList list;
//    concurrent.CopyOnWriteArrayList 可满足并发要求 性能会更好
//    private final CopyOnWriteArrayList list = new CopyOnWriteArrayList();

    public Pool(LinkedList list) {
        // 构造方法中允许用户传入自己需要的list集合（外来的方法）
        this.list = list;
    }

    public void add(Integer num) {
        synchronized (list) {
            list.add(num);
        }
    }

    public void remove(Integer num) {
        synchronized (list) {
            list.remove(num);
        }
    }

    public Integer size() {
        return list.size();
    }
}
