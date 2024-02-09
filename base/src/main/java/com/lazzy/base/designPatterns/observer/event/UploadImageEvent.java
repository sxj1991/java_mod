package com.lazzy.base.designPatterns.observer.event;


import com.lazzy.base.designPatterns.observer.domain.ImageInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 继承ApplicationEvent
 * 监听者 事件类型
 * 注入spring ApplicationEventPublisher类型 推送消息事件
 */
public class UploadImageEvent extends ApplicationEvent {


    public UploadImageEvent(ImageInfo source) {
        super(source);
    }

}
