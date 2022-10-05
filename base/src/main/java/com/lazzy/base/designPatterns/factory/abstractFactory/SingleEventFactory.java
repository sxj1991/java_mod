package com.lazzy.base.designPatterns.factory.abstractFactory;


import com.lazzy.base.designPatterns.factory.abstractFactory.entity.RandomEvent;
import com.lazzy.base.designPatterns.factory.abstractFactory.entity.SingleEvent;

public interface SingleEventFactory extends EventFactory{
    SingleEvent createSingle();

    default RandomEvent createRandom() {
        return null;
    }


}
