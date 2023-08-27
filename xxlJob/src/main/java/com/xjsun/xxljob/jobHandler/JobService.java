package com.xjsun.xxljob.jobHandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 *      1、任务开发：在Spring Bean实例中，开发Job方法；
 *      2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 *      3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 *      4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 *
 */
@Component
public class JobService {
    private static Logger logger = LoggerFactory.getLogger(JobService.class);


    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("simpleJobHandler")
    public void jobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
        }
        hasException();
        // 默认成功
        Thread currentThread = Thread.currentThread();
        logger.info("线程信息:{},{}",currentThread.getName(),currentThread.getId());
        logger.info("hello xxl");
    }

    private void hasException(){
        try {
            Thread currentThread = Thread.currentThread();
            logger.info("线程信息:{},{}",currentThread.getName(),currentThread.getId());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            logger.error("错误信息：",e);
        }
    }
}
