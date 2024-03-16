package com.lazzy.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * quartz定时任务框架 二次封装
 * 提供http 接口控制任务的启动删除
 * 如果存在application.yaml,其quartz.properties文件需要在yaml中单独配置
 * quartz 支持集群模式共用数据库，同时也有监听器监听任务执行情况，但是缺少告警和日志需要用户自己添加
 */
@SpringBootApplication
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }

}
