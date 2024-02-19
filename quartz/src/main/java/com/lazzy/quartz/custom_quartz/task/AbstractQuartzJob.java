package com.lazzy.quartz.custom_quartz.task;

import java.util.Date;

import com.lazzy.quartz.custom_quartz.common.ScheduleConstants;
import com.lazzy.quartz.custom_quartz.entity.SysJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * 抽象quartz调用
 *
 */
public abstract class AbstractQuartzJob implements Job
{
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        SysJob sysJob = new SysJob();
        // 获取jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job)放入的sysjob对象
        SysJob taskData = (SysJob) context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES);
        BeanUtils.copyProperties(taskData,sysJob);
        try
        {

            if (sysJob != null) {
                doExecute(context, sysJob);
            }

        }
        catch (Exception e)
        {
            log.error("任务执行异常  - ：", e);
        }
    }




    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}