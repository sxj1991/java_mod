package com.xjsun.flow.service;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * liteflow工作流启动类
 */
@Service
public class LiteService implements ApplicationRunner {
    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LiteflowResponse response = flowExecutor.execute2Resp("liteChain");
        System.out.println(response.getExecuteStepStr());
    }
}
