package com.lazzy.base.designPatterns.factory.abstractFactory.entity;

import org.springframework.stereotype.Component;

@Component
public class RandomEvent implements IEvent {
    public void print(){
        System.out.println("this is random method");
    }
}
