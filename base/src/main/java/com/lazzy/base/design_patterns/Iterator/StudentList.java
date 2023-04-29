package com.lazzy.base.design_patterns.Iterator;

import java.util.List;

/**
 * 实现容器接口添加 删除元素 获取迭代器的方法
 * @param <T>
 */
public class StudentList<T> implements Container<T>{
   private final List<T> names;

    public StudentList(List<T> names) {
        this.names = names;
    }

    @Override
    public void add(T t) {
        names.add(t);
    }

    @Override
    public void remove(T t) {
       names.remove(t);
    }

    @Override
    public Iterator<T> iterator() {
        return new NameIterator<>();
    }

    /**
     * 自定义迭代器 判断是否存在下一个元素，和获取元素
     * @param <T>
     */
    private class NameIterator<T> implements Iterator<T> {

        int index;

        @Override
        public boolean hasNext() {
            return index < names.size();
        }

        @Override
        public T next() {
            if(this.hasNext()){
                return (T) names.get(index++);
            }
            return null;
        }
    }
}
