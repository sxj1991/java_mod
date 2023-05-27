package com.lazzy.base.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * strPowerMock mock的类注入到该类
 */
@Component
@Slf4j
public class StrPowerService {

    /**
     * 模拟服务层调用静态方法
     * 如果实际调用静态方法会抛出异常，即测试时需要mock静态方法正常返回数据让测试通过
     * @return mock数据
     */
    public String getThrow(){
        log.info("getThrow调用>>>>>>>>>");
        StrPowerMock.staticHasThrow();
        return StrPowerMock.staticHasThrowStr();
    }
}
