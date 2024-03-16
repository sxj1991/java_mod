package com.lazzy.quartz.custom_quartz.task;

import com.lazzy.quartz.custom_quartz.entity.SysJob;
import com.lazzy.quartz.custom_quartz.invoke.JobInvokeUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 * DisallowConcurrentExecution 阻止定时器并行执行
 *
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}