package com.lazzy.base.design_patterns.strategy.factory;


import com.lazzy.base.design_patterns.strategy.domain.Message;
import com.lazzy.base.design_patterns.strategy.service.MessageService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息策略工厂类
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
