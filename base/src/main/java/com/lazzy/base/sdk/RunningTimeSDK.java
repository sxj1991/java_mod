package com.lazzy.base.sdk;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author mrs.sun
 * @title: LogConfiguration
 * @description: 配置类 目的是为切面 生效
 * @date 2021/11/2016:35
 */
@Configuration
//SpringApplication 存在时 调用该配置类 springboot项目使用
@ConditionalOnClass(SpringApplication.class)
public class RunningTimeSDK extends AbstractPointcutAdvisor implements InitializingBean {
    private Pointcut pointcut;

    private Advice advice;
    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.pointcut = new AnnotationMatchingPointcut(null, LogExcute.class);
        this.advice = new LogAdvice();
    }
}
