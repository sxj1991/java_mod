package com.lazzy.mongodb.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * mongodb配置类
 *
 */
@Configuration
@Slf4j
public class MongodbConfig {
    /**
     * mongodb事务配置
     *
     * @param factory 工厂
     * @return 事务管理器
     */
    @Bean("mongoTransactionManager")
    public MongoTransactionManager transactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }

    /**
     * mysql事务管理器
     *
     * @param dataSource 数据源
     * @return 事务管理器
     * @Bean
     * @Primary
     * public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
     *       return new DataSourceTransactionManager(dataSource);
     * }
     */

}