package com.lazzy.base.designPatterns.decorator;

/**
 * 装饰器模式
 * 通过传入原始type类，在该类的基础上对其增强，对类型修改进行解耦。
 * 宛如对原始类装饰一般。
 */
public abstract class TypeDecorator extends Type {
    private Type delegate;

    public TypeDecorator(Type type) {
        this.delegate = type;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

}