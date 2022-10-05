package com.lazzy.quartz.ruoyi_quartz.task;

import org.springframework.stereotype.Component;

@Component("task")
public class CronTask {
    public void taskNoParams()
    {
        System.out.println("执行无参方法");
    }
}
