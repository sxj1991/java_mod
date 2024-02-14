package com.lazzy.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分区初始化配置(选配)
 * docker测试 kafka运行指令
 * docker run -d --name zookeeper -p 2182:2181 --restart=always  -e ALLOW_ANONYMOUS_LOGIN=true -t bitnami/zookeeper
 *
 * docker run -d --name kafka -p 9092:9092 --restart=always -e KAFKA_CFG_ZOOKEEPER_CONNECT=192.168.0.102:2182 -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://192.168.0.102:9092 -e KAFKA_CFG_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e ALLOW_PLAINTEXT_LISTENER=yes -t bitnami/kafka
 */
//@Configuration
public class KafkaInitialConfiguration {

    // 创建一个名为testtopic的Topic并设置分区数为8，分区副本数为2
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("topic-test-llc",1, (short) 1 );
    }
    
    // 如果要修改分区数，只需修改配置值重启项目即可
    // 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
    @Bean
    public NewTopic updateTopic() {
        return new NewTopic("test-topic",1, (short) 1 );
    }

}
