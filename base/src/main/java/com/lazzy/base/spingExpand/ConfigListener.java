package com.lazzy.base.spingExpand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * spring 监听器 监听spring生命周期事件
 */
@Slf4j
public class ConfigListener implements SmartApplicationListener {
    /**
     * 选择支持的事件类型
     * @param eventType 事件类型
     * @return 返回布尔值
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType);
    }

    /**
     * 事件触发 修改配置
     * @param event 事件类型
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("触发监听事件");
        if(event instanceof ApplicationEnvironmentPreparedEvent){
            ApplicationEnvironmentPreparedEvent environmentPreparedEvent =
                    (ApplicationEnvironmentPreparedEvent) event;
            ConfigurableEnvironment environment = environmentPreparedEvent.getEnvironment();
            log.warn("读取配置文件修改前:"+ environment.getProperty("mail.url"));
            System.setProperty("mail.url","test");
            log.warn("读取配置文件修改后:"+ environment.getProperty("mail.url"));
        }
    }
}
