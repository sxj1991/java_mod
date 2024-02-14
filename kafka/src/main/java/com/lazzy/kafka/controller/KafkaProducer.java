package com.lazzy.kafka.controller;


import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazzy.kafka.model.LogMsg;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * kafka 消息队列生产者
 * 全局有序：一个Topic下的所有消息都需要按照生产顺序消费。
 * 局部有序：一个Topic下的消息，只需要满足同一业务字段的要按照生产顺序消费。
 * 模拟多节点日志存储保证有序性
 */
@RestController
public class KafkaProducer {
    // 注入对象
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // 发送消息
    @GetMapping("/kafka/normal/{message}")
    public String sendMessage(@PathVariable("message") String normalMessage) {
        //向消息队列发送信息
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 执行 10 次时间格式化
        for (int i = 0; i < 10; i++) {
            // 线程池执行任务
            threadPool.execute(() -> {
                // 执行时间格式化并打印结果
                kafkaTemplate.send("topic-test-llc", custom(new LogMsg(time(), normalMessage)));
            });
        }

        return "ok";
    }

    private static String custom(LogMsg logMsg) {
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(logMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String time() {
        Date date = new Date();

        // 创建日期格式化对象，指定要序列化的日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 将日期对象格式化为指定格式的字符串
        return sdf.format(date);
    }
}

