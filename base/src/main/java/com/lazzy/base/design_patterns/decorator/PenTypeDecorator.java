package com.lazzy.base.design_patterns.decorator;

public class PenTypeDecorator extends TypeDecorator{
    public PenTypeDecorator(Type type) {
        super(type);
    }

    @Override
    public String getName() {
        return "笔:"+super.getName();
    }
}
