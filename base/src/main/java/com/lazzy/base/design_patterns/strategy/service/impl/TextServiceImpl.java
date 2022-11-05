package com.lazzy.base.design_patterns.strategy.service.impl;


import com.lazzy.base.design_patterns.strategy.domain.Message;
import com.lazzy.base.design_patterns.strategy.factory.MessageServiceStrategyFactory;
import com.lazzy.base.design_patterns.strategy.message.MessageProcess;
import com.lazzy.base.design_patterns.strategy.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 文本策略类
 */
@Slf4j
@Component
public class TextServiceImpl implements MessageService, InitializingBean {
    @Override
    public String processMsg(Message msg) {
        log.info("执行文本消息策略");
        msg.setMsg("该消息已处理:"+msg.getMsg());
        return msg.getMsg();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageServiceStrategyFactory.register(MessageProcess.Text.name(),this);
    }
}
