package com.lazzy.base.design_patterns.observer.event;


import com.lazzy.base.design_patterns.observer.domain.ImageInfo;
import org.springframework.context.ApplicationEvent;

public class UploadImageEvent extends ApplicationEvent {


    public UploadImageEvent(ImageInfo source) {
        super(source);
    }

}
