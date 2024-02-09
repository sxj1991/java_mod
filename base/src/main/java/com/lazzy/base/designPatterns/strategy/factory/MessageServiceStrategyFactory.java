package com.lazzy.base.designPatterns.strategy.factory;


import com.lazzy.base.designPatterns.strategy.domain.Message;
import com.lazzy.base.designPatterns.strategy.service.MessageService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息策略工厂类
 * 通过不同策略类型spring 扩展InitializingBean接口初始化策略类型，加入内存缓存，通过map获取类型信息调用不同策略方法。
 * 当程序变化新增需求，只需要新增策略类型即可。
 */
public abstract class MessageServiceStrategyFactory {
    private static Map<String, MessageService> services = new ConcurrentHashMap<String, MessageService>();

    //获取消息策略类对象
    public static String processMsgType(String msgType, Message msg) {
        return services.get(msgType).processMsg(msg);
    }

    //注册不同消息策略入缓存
    public static void register(String msgType, MessageService msgService) {
        services.put(msgType, msgService);
    }

}
