package com.lazzy.quartz.base_quartz.task;


import com.lazzy.quartz.base_quartz.trigger.SimpleTrigger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
public class TriggerStart implements CommandLineRunner {

   @Resource
   private SimpleTrigger simpleTrigger;

    /**
     * CommandLineRunner 接口run方法 程序启动执行
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        simpleTrigger.start();
    }




}
