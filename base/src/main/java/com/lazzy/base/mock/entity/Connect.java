package com.lazzy.base.mock.entity;

/**
 * 用来测试方法中new 对象的mock 实体类
 */
public class Connect {

    /**
     * 测试中需要mock为false 否则会抛出异常
     * @return true
     */
    public Boolean getConnected() {
        return true;
    }
}
