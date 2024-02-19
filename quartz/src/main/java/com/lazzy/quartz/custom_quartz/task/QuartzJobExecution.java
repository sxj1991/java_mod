package com.lazzy.quartz.custom_quartz.task;

import com.lazzy.quartz.custom_quartz.entity.SysJob;
import com.lazzy.quartz.custom_quartz.invoke.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 *
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob{

    //quartz 会执行该方法
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}