package com.lazzy.base.designPatterns.factory.abstractFactory.impl;


import com.lazzy.base.designPatterns.factory.abstractFactory.RandomEventFactory;
import com.lazzy.base.designPatterns.factory.abstractFactory.entity.RandomEvent;

public class RandomImpl implements RandomEventFactory {

    @Override
    public RandomEvent createRandom() {
        return new RandomEvent();
    }
}
