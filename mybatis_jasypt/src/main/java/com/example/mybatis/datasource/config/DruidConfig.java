package com.example.mybatis.datasource.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.example.mybatis.datasource.DynamicDataSource;
import com.example.mybatis.enums.DataSourceType;
import com.example.mybatis.util.SpringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库配置类
 */
@Configuration
public class DruidConfig {
    @Value("${spring.datasource.druid.slave.enabled}")
    private Boolean bool;

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    /**
     * @param
     * @return
     * @ConfigurationProperties 通过配置文件进行绑定，然后将此Bean归还给容器 配置@Bean使用
     * @ConditionalOnProperty 该注解作用 havingValue根据这个值和配置文件比较 是否启动该配置
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource() {
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        if(bool){
            setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveDataSource");
        }
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    /**
     * 设置数据源
     *
     * @param targetDataSources 备选数据源集合
     * @param sourceName        数据源名称
     * @param beanName          bean名称
     */
    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
        try {
            DataSource dataSource = SpringUtils.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
        } catch (Exception e) {
            //改善ruoyi框架不捕捉异常信息的问题 注入value判断
            e.printStackTrace();
        }
    }
}
