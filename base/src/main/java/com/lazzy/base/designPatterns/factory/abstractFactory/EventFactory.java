package com.lazzy.base.designPatterns.factory.abstractFactory;


import com.lazzy.base.designPatterns.factory.abstractFactory.entity.RandomEvent;
import com.lazzy.base.designPatterns.factory.abstractFactory.entity.SingleEvent;

/**
 * 抽象工厂模式
 */

public interface EventFactory {
  RandomEvent createRandom();
  SingleEvent createSingle();
}
