package com.lazzy.base.effectiveJava.composition_over_inheritance;

import java.util.List;

/**
 * BList<T> 组合方式
 * 优点：不用关心 RootList实现方式，代码更加健壮，以及更易维护
 * 缺点：增加BList代码复杂性
 */
public class BList<T> {
    private RootList<T> rootList = new RootList();

    private int count;

    public void add(T t){
        System.out.println("AList添加单个元素");
        count++;
        rootList.add(t);
    }

    public void addAll(List<T> t){
        System.out.println("AList添加所有元素");
        count += t.size();
        rootList.addAll(t);
    }

    public int getCount(){
        return count;
    }
}
