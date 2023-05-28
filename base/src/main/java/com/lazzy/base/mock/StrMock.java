package com.lazzy.base.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 测试springboot mock 框架
 * 1.有返回值isEmpty方法需要mock
 * 2.无返回值hasThrow方法一旦真实调用会抛出异常
 */
@Slf4j
@Component
public class StrMock {

    public boolean isEmpty(String str){
        log.info("测试判断字符串:{}",str);
        return StringUtils.hasText(str);
    }

    public void hasThrow(){
        throw new RuntimeException("测试忽略方法失败，调用抛异常");
    }
}
