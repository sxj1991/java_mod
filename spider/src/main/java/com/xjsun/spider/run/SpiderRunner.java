package com.xjsun.spider.run;

import com.xjsun.spider.http.HttpPageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动程序运行爬虫程序
 */
@Component
@Slf4j
public class SpiderRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        // 创建一个时间调度器 间隔5秒执行一次爬虫任务
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    HttpPageProcessor.start();
                } catch (Exception e) {
                    log.error("启动爬虫失败：" + e.getMessage(), e);
                }

            }
        }, 1000L * 5, 1000L * 5);
    }
}
