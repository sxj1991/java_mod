package com.lazzy.base.effectiveJava.avoid_excessive_synchronization;

import java.util.LinkedList;

/**
 * 避免过度同步 错误展示
 * synchronized (list){
 *     list.add(num);
 *  }
 *  由于引入外来重写覆盖（list）的add方法，导致同步范围扩大，导致每个线程都会睡眠1秒钟执行
 *  其中外来方法的动作是不可预测的，如果在添加或者遍历种，用户还进行删除操作，则会抛出运行时异常。
 */
public class PoolError {
    public static void main(String[] args) {
        Pool pool = new Pool(new LinkedList() {
            @Override
            public boolean add(Object o) {
                try {
                    //模拟处理效果
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return super.add(o);
            }
        });

        for (int i = 0; i <5 ; i++) {
            int num = i;
            new Thread(()->{
                pool.add(num);
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
