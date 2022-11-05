package com.lazzy.base.design_patterns.factory.simple;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 事件简单工厂模式
 */
@Component
public class EventFactory {

    @Resource
    private List<IEvent> iEventList;

    public IEvent create(Class<? extends IEvent> clazz){
        System.out.println(iEventList.size());
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
