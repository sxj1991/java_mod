package com.lazzy.base.design_patterns.decorator;

public class BookTypeDecorator extends TypeDecorator{

    public BookTypeDecorator(Type type) {
        super(type);
    }

    @Override
    public String getName() {
        return "书本："+super.getName();
    }
}
