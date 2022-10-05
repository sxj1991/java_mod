package com.lazzy.quartz.base_quartz.trigger;


import com.lazzy.quartz.base_quartz.task.TaskJob;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Component
public class SimpleTrigger {
    @Resource
    private Scheduler scheduler;

    public void start() throws SchedulerException {
        TriggerKey triggerKeys = TriggerKey.triggerKey("trigger", "triggerGroup");
        CronTrigger triggers = (CronTrigger) scheduler.getTrigger(triggerKeys);
        //判断触发器是否存在
        if (ObjectUtils.isEmpty(triggers)) {
            // 定义任务调度实例, 并与TaskJob绑定
            JobDetail jobDetail = JobBuilder.newJob(TaskJob.class)
                    //job名称和分组名
                    .withIdentity("taskJob", "taskJobGroup")
                    .storeDurably(true)
                    .build();
            // 定义触发器, 会马上执行一次, 接着5秒执行一次
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger", "triggerGroup")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 */5 * * * ? "))
                    .build();
            // 使用触发器调度任务的执行
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } else {
            //触发器存在则执行 原来的触发器 必要时可以根据需要在重新修改
            Trigger trigger = triggers.getTriggerBuilder()
                    .withIdentity("trigger", "triggerGroup")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ? "))
                    .build();
            scheduler.rescheduleJob(triggerKeys, trigger);
            scheduler.start();
        }

    }

}
