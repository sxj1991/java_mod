package com.lazzy.base.effectiveJava.composition_over_inheritance;

import java.util.List;

/**
 * 组合优于继承
 * 原因讲解
 * RootList 父类
 */
public class RootList<T> {
    private int count;

    public void add(T t){
        System.out.println("root添加单个元素");
        count++;
    }

    public void addAll(List<T> t){
        System.out.println("root添加所有元素");
        for (T t1 : t) {
            // 调用add方法
            this.add(t1);
        }
    }
}
