package com.lazzy.base.effectiveJava.avoid_excessive_synchronization;

import java.util.LinkedList;

/**
 * 避免过度同步 正确展示
 * 1. 简单修改：
 * 1.1缩减同步代码块，睡眠1秒放在锁定的代码块外围
 * 1.2去掉覆写的代码块，同步种会带来不可控因素
 * 1.3不要给用户提供可覆写的接口，会导致同步代码块出现不可预测的情况
 */
public class PoolCorrect {
    public static void main(String[] args) {
        // 不要给用户提供可覆写的接口
        Pool pool = new Pool(new LinkedList()
//        {
//            @Override
//            public boolean add(Object o) {
//                不要在同步代码块种覆写方法
//                return super.add(o);
//            }
//        }
        );

        for (int i = 0; i < 5; i++) {
            int num = i;
            new Thread(() -> {
                pool.add(num);
                try {
                    //
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        try {
            //模拟处理效果
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(pool.size());

    }
}
