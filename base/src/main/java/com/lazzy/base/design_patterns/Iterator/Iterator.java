package com.lazzy.base.design_patterns.Iterator;

/**
 * 自定义迭代器接口
 * 不同的迭代器实例实现该接口实现不同的迭代功能
 * 容器通过内部迭代器具体实现，实现遍历数据和更新数据的解耦操作
 */
public interface Iterator<T> {
    /**
     * 是否存在下一个元素
     * @return 布尔值
     */
    boolean hasNext();

    /**
     * 下一个元素对象
     * @return 对象
     */
    T next();
}
