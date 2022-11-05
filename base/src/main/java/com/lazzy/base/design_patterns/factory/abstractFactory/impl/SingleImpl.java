package com.lazzy.base.design_patterns.factory.abstractFactory.impl;


import com.lazzy.base.design_patterns.factory.abstractFactory.SingleEventFactory;
import com.lazzy.base.design_patterns.factory.abstractFactory.entity.SingleEvent;

public class SingleImpl implements SingleEventFactory {

    @Override
    public SingleEvent createSingle() {
        return new SingleEvent();
    }
}
