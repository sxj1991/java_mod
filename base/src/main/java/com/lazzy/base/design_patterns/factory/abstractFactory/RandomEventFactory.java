package com.lazzy.base.design_patterns.factory.abstractFactory;


import com.lazzy.base.design_patterns.factory.abstractFactory.entity.RandomEvent;
import com.lazzy.base.design_patterns.factory.abstractFactory.entity.SingleEvent;

public interface RandomEventFactory extends EventFactory{
    RandomEvent createRandom();


    default SingleEvent createSingle() {
        return null;
    }
}
