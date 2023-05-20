package com.xjsun.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel("消息实体类")
@AllArgsConstructor
public class Message {
    @ApiModelProperty("响应消息")
    private String msg;
}
