package com.lazzy.base.designPatterns.strategy.service;


import com.lazzy.base.designPatterns.strategy.domain.Message;

/**
 * 信息处理方法接口
 */

public interface MessageService {
    /**
     * 信息处理方法
     * @param msg 消息对象
     * @return 信息处理结果
     */
    String processMsg(Message msg);
}
