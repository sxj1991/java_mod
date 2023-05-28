package com.lazzy.base.designPatterns.observer.domain;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ImageInfo  {
    private String imageName;
    private String imageUrl;
}
