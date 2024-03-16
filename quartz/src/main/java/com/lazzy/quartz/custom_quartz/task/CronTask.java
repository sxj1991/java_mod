package com.lazzy.quartz.custom_quartz.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component("task")
public class CronTask {
    public void taskNoParams()
    {
        System.out.println("执行无参方法");
    }
}
