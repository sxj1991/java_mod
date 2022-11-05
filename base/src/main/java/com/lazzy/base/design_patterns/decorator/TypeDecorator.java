package com.lazzy.base.design_patterns.decorator;

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