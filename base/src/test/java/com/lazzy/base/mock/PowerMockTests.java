package com.lazzy.base.mock;

import com.lazzy.base.BaseApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;


import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * powerMockito 测试类
 * 需要注意的是 jdk17 使用会出现兼容问题
 * 实际测试类是使用jdk 1.8版本 springboot 版本 2.4.3
 * 其中mockito与powerMockito可能出现兼容问题需要pom配置单独配置
 * 选择<mockito.version>3.6.28</mockito.version>版本不作为参考根据实际情况写
 *
 */
@RunWith(PowerMockRunner.class)
@SpringBootTest(classes = BaseApplication.class)
@Slf4j
@PrepareForTest(StrPowerMock.class)
public class PowerMockTests {

    // 需要mock的类
    @InjectMocks
    private StrPowerMock strPowerMock;

    /**
     * 私有方法mock测试
     * strPowerMock 自己内部私有方法测试
     */
    @Test
    public void powerMock() {
        // 如果是私有方法 需要把这个类监控起来
        StrPowerMock mock = PowerMockito.spy(strPowerMock);
        try {
            // 通过此方式调用私有方法
            PowerMockito.when(mock, "privateIsEmpty", "bool").thenReturn(false);
            // 参数必须一致
            Boolean test = mock.isEmpty("bool");
            log.info("res:{}", test);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * mock 静态方法测试
     */
    @Test
    public void powerStatic() {
        try{
            // 如果静态方法没有返回值 则不用调用when方法
            PowerMockito.mockStatic(StrPowerMock.class);
            // 静态方法mock 返回值
            PowerMockito.when(StrPowerMock.staticHasThrowStr()).thenReturn("test");
            StrPowerService strPowerService = new StrPowerService();
            // 获取mock的返回值并输出日志
            String str = strPowerService.getThrow();
            log.info("测试完毕>>>>>>{}",str);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
