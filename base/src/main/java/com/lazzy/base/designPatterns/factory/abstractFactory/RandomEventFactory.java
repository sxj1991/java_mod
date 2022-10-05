package com.lazzy.base.designPatterns.factory.abstractFactory;


import com.lazzy.base.designPatterns.factory.abstractFactory.entity.RandomEvent;
import com.lazzy.base.designPatterns.factory.abstractFactory.entity.SingleEvent;

public interface RandomEventFactory extends EventFactory{
    RandomEvent createRandom();


    default SingleEvent createSingle() {
        return null;
    }
}
