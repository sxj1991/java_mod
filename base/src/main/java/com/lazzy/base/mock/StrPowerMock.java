package com.lazzy.base.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * powerMockito 框架集成测试
 * 弥补mockito 框架不能mock 私有方法 final方法 静态方法...等缺陷
 */
@Slf4j
public class StrPowerMock {
    /**
     * 此方法供外部调用，mock 自身静态方法
     */
    public Boolean isEmpty(String bool){
        log.info("开始isEmpty调用>>>>>>>>>");
        Boolean res = privateIsEmpty(bool);
        log.info("结束isEmpty调用>>>>>>>>>");
        return res;
    }

    /**
     * mock 私有方法
     */
    private Boolean privateIsEmpty(String bool){
        return StringUtils.hasText(bool);
    }

    /**
     * mock 静态方法
     *
     */
    public static void staticHasThrow(){
        throw new RuntimeException("静态方法没有mock成功");
    }

    /**
     * mock 静态方法
     *
     */
    public static String staticHasThrowStr(){
        throw new RuntimeException("静态方法没有mock成功");
    }
}
