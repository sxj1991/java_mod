package com.lazzy.base.designPatterns.observer.domain;

import lombok.Builder;
import lombok.ToString;

/**
 * 事件传递的数据
 */
@Builder
@ToString
public class ImageInfo  {
    private String imageName;
    private String imageUrl;
}
