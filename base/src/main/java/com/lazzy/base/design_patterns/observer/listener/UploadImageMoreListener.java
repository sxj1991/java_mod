package com.lazzy.base.design_patterns.observer.listener;


import com.lazzy.base.design_patterns.observer.domain.ImageInfo;
import com.lazzy.base.design_patterns.observer.event.UploadImageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *  图片上传监听器
 *  一旦有事件 applicationContext.publishEvent(event);
 *  则会触发监听器内方法
 */
@Component
@Slf4j
public class UploadImageMoreListener implements ApplicationListener<UploadImageEvent> {

    @Override
    @Async
    public void onApplicationEvent(UploadImageEvent event) {
        if(event.getSource() instanceof ImageInfo){
            log.info("UploadImageMoreListener获取上传文件信息:"+event.getSource());
        }

    }
}
