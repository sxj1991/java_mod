package com.lazzy.base.designPatterns.factory.simple;

import org.springframework.stereotype.Component;

@Component
public class PrintEvent implements IEvent{

    public void print(){
        System.out.println("this is print method");
    }
}
