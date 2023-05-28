package com.lazzy.base.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 需要测试的方法
 */
@Slf4j
@Component
public class StrService {
    @Resource
    private StrMock strMock;

    public boolean empty(String str) {
        log.info("StrService调用empty方法");
        return strMock.isEmpty(str);
    }

    public boolean hasThrow(String str) {
        strMock.hasThrow();
        log.info("StrService调用hasThrow方法");
        return strMock.isEmpty(str);
    }
    




}
