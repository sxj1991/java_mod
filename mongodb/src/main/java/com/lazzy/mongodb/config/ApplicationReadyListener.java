package com.lazzy.mongodb.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * @author xjsun
 * @version 1.0
 * 监听mongo 保存数据
 * 此类若不加，那么插入的一行会默认添加一个_class字段来存储实体类类型 如（com.example.demo.entity.Student）
 */
@Configuration
public class ApplicationReadyListener implements ApplicationListener<ContextRefreshedEvent> {

  private final MongoTemplate oneMongoTemplate;
  
  private static final String TYPEKEY = "_class";

  public ApplicationReadyListener(MongoTemplate oneMongoTemplate) {
    this.oneMongoTemplate = oneMongoTemplate;
  }

  @Override
  public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {
    MongoConverter converter = oneMongoTemplate.getConverter();
    if (converter.getTypeMapper().isTypeKey(TYPEKEY)) {
      ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
    }
  }
}
