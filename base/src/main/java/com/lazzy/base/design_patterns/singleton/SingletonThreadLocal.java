package com.lazzy.base.design_patterns.singleton;

/**
 * 通过ThreadLocal 线程局部变量
 * 单例模式
 *
 */
public class SingletonThreadLocal {
    private static final ThreadLocal<SingletonThreadLocal> threadLocal =
            new ThreadLocal<SingletonThreadLocal>(){
                @Override
                protected SingletonThreadLocal initialValue() {
                    return new SingletonThreadLocal();
                }
            };
    private SingletonThreadLocal(){

    }
    //此种单例模式 不能保证整个应用全局唯一，但是可以保证线程唯一
    public static SingletonThreadLocal getInstance(){
        return threadLocal.get();
    }

}
