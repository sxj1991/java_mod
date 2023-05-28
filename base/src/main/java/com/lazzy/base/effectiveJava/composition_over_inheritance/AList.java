package com.lazzy.base.effectiveJava.composition_over_inheritance;

import java.util.List;

/**
 * ALis继承方式
 * 优点：代码实现更加简单
 * 缺点：
 * 1.继承关系一旦多个，必然导致代码结构复杂化，其继承体系也会产生类似AList add方法所产生的bug调用者必须对其父类实现有所了解，但父类方法可能非自己维护，极大影响开发效率。
 * 2.继承体系中，子类不可避免的拥有父类一切方法，一旦需求中子类不需要某个方法时，除了抛出异常外或者空实现其方法，否则无法实现该需求
 * 3.方法、属性权限只能扩大不能缩小，返回值不可修改（ public protect private） 伸缩性不好
 */
public class AList<T> extends RootList<T>{
    private int count;

    /**
     * bug点：
     * addAll方法，调用了父类的方法，父类实现中存在调用RootList add方法
     * 由于继承关系，其add方法重写后，会调用AList add方法 不可避免的产生count计算错误
     */
    public void add(T t){
        System.out.println("AList添加单个元素");
        count++;
    }

    public void addAll(List<T> t){
        System.out.println("AList添加所有元素");
        count += t.size();
        super.addAll(t);
    }

    public int getCount(){
        return count;
    }
}
