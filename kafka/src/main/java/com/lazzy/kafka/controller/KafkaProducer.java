package com.lazzy.kafka.controller;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka 消息队列生产者
 */
@RestController
public class KafkaProducer {
    // 注入对象
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // 发送消息
    @GetMapping("/kafka/normal/{message}")
    public String sendMessage1(@PathVariable("message") String normalMessage) {
            //向消息队列发送信息
            kafkaTemplate.send("topic-test-llc", normalMessage);
        return "ok";
    }
}

