package com.lazzy.base.designPatterns.observer.event;


import com.lazzy.base.designPatterns.observer.domain.ImageInfo;
import org.springframework.context.ApplicationEvent;

public class UploadImageEvent extends ApplicationEvent {


    public UploadImageEvent(ImageInfo source) {
        super(source);
    }

}
