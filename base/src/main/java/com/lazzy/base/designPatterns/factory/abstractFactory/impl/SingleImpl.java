package com.lazzy.base.designPatterns.factory.abstractFactory.impl;


import com.lazzy.base.designPatterns.factory.abstractFactory.SingleEventFactory;
import com.lazzy.base.designPatterns.factory.abstractFactory.entity.SingleEvent;

public class SingleImpl implements SingleEventFactory {

    @Override
    public SingleEvent createSingle() {
        return new SingleEvent();
    }
}
