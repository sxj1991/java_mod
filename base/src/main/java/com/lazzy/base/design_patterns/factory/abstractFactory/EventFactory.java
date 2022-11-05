package com.lazzy.base.design_patterns.factory.abstractFactory;


import com.lazzy.base.design_patterns.factory.abstractFactory.entity.RandomEvent;
import com.lazzy.base.design_patterns.factory.abstractFactory.entity.SingleEvent;

/**
 * 抽象工厂模式
 */

public interface EventFactory {
  RandomEvent createRandom();
  SingleEvent createSingle();
}
