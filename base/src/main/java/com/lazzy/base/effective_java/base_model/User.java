package com.lazzy.base.effective_java.base_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 测试实体类
 * 1. 重写toString方法
 * 2. 重写equals方法
 * 3. 重写hashcode方法（不重写 则hash值不相同）
 * 4. 实现cloneable接口 浅克隆（注意事项） 例如属性中包含集合 则克隆对象的引用地址和原地址相同，一旦修改集合两者都会改变
 */
public class User implements Cloneable {
    private String name;

    private Integer age;

    private List<String> hobbit;

    public User(String name, Integer age, List<String> hobbit) {
        this.name = name;
        this.age = age;
        this.hobbit = hobbit;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hobbit=" + hobbit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(age, user.age);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("soccer");
        User user = new User("lin", 22, arrayList);
        User clone = (User) user.clone();
        clone.hobbit.add("baseball");
        System.out.println(user);

    }
}
