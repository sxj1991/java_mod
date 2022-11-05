package com.lazzy.base.design_patterns.factory.abstractFactory.impl;


import com.lazzy.base.design_patterns.factory.abstractFactory.RandomEventFactory;
import com.lazzy.base.design_patterns.factory.abstractFactory.entity.RandomEvent;

public class RandomImpl implements RandomEventFactory {

    @Override
    public RandomEvent createRandom() {
        return new RandomEvent();
    }
}
