package com.lazzy.base.juc.thread_base;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程死锁
 * 死锁是这样一种情形：多个线程同时被阻塞，它们中的一个或者全部都在等待某个资源被释放。由于线程被无限期地阻塞，因此程序不可能正常终止。
 *
 * java 死锁产生的四个必要条件：
 *
 * 1、互斥使用，即当资源被一个线程使用(占有)时，别的线程不能使用
 * 2、不可抢占，资源请求者不能强制从资源占有者手中夺取资源，资源只能由资源占有者主动释放。
 * 3、请求和保持，即当资源请求者在请求其他的资源的同时保持对原有资源的占有。
 * 4、循环等待，即存在一个等待队列：P1占有P2的资源，P2占有P3的资源，P3占有P1的资源。这样就形成了一个等待环路。
 * 当上述四个条件都成立的时候，便形成死锁。当然，死锁的情况下如果打破上述任何一个条件，便可让死锁消失。下面用java代码来模拟一下死锁的产生
 *
 * 使用jave自带工具查找死锁位置
 * 1. jps查询java进程id
 * 2. jstack id 查询死锁具体信息
 */
@Slf4j
public class DeadLock {
    //两把锁
    private final Object lock1 = new Object();

    private final Object lock2 = new Object();

    /**
     * 两个线程互相需要对方资源，且自身资源又无法释放，造成死锁
     */
    public void run(){
        new Thread(()->{
            synchronized(lock1){
                try {
                    log.info("获取lock1,线程睡眠1秒，获取lock2，执行后续代码");
                    Thread.sleep(1000);
                     synchronized (lock2){
                         log.info("获取到lock2");
                     }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(()->{
            synchronized(lock2){
                log.info("获取lock2,线程睡眠1秒，获取lock1，执行后续代码");
                try {
                    Thread.sleep(1000);
                    synchronized (lock1){
                        log.info("获取到lock1");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }
}
