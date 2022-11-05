package com.lazzy.base.design_patterns.observer.domain;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ImageInfo  {
    private String imageName;
    private String imageUrl;
}
