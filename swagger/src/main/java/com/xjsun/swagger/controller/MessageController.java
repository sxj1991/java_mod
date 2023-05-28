package com.xjsun.swagger.controller;

import com.xjsun.swagger.entity.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息控制类
 * 测试swagger
 */
@Api(tags = "测试接口")
@RestController("/msg")
public class MessageController {
    @GetMapping(value = "/status/{msg}")
    @ApiOperation("消息接口")
    public Message msg(@PathVariable @ApiParam("消息参数") String msg) {

        return new Message("接口响应成功:" + msg);
    }
}
