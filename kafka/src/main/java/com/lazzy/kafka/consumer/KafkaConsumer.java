package com.lazzy.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    // 消费监听
    @KafkaListener(topics = {"topic-test-llc"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("简单消费Topic："+record.topic()+",分区"+record.partition()+",值内容"+record.value());
    }
}
