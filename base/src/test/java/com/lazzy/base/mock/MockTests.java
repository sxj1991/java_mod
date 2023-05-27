package com.lazzy.base.mock;

import com.lazzy.base.BaseApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * mockito 测试框架
 * 1. springboot 测试框架 适合组件注入mock的情形
 * 2. 无法mock 静态方法 final方法 私有方法 函数内创建的对象...等等情况
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BaseApplication.class)
@Slf4j
public class MockTests {

    @MockBean
    private StrMock mock;

    @Autowired
    private StrService strService;

    @Test
    public void strEmpty(){
        Mockito.when(mock.isEmpty(Mockito.anyString())).thenReturn(true);
        log.info("result:{}",strService.empty("test"));
    }

    @Test
    public void hasThrow(){
        Mockito.doNothing().when(mock).hasThrow();
        Mockito.when(mock.isEmpty(Mockito.anyString())).thenReturn(true);
        log.info("result:{}",strService.hasThrow("test"));
    }
}
