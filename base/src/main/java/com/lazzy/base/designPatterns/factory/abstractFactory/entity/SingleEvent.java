package com.lazzy.base.designPatterns.factory.abstractFactory.entity;

public class SingleEvent implements IEvent{
    @Override
    public void print(){
        System.out.println("this is single method");
    }
}
