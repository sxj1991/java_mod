package com.lazzy.base.design_patterns.factory.abstractFactory;


import com.lazzy.base.design_patterns.factory.abstractFactory.entity.RandomEvent;
import com.lazzy.base.design_patterns.factory.abstractFactory.entity.SingleEvent;

public interface SingleEventFactory extends EventFactory{
    SingleEvent createSingle();

    default RandomEvent createRandom() {
        return null;
    }


}
