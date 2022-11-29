package com.lazzy.base.sdk;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mrs.sun
 * @title: LogAdvice
 * @description: aop方法切面 计算方法耗时
 * @date 2021/11/2015:55
 * * 无法使用 @Aspect aop库切面来拦截方法 无法转化为advice类
 */
public class LogAdvice implements MethodInterceptor {

    private static final Logger LOGGER =(Logger) LoggerFactory.getLogger(LogAdvice.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LogExcute execute = methodInvocation.getMethod().getAnnotation(LogExcute.class);
       //如果该注解为null则直接放行
        if (execute==null){
            return methodInvocation.proceed();
        }
        //计算方法执行的时间
        long start = System.currentTimeMillis();
         Object result = methodInvocation.proceed();
        long end = System.currentTimeMillis();
        //打印执行日志
        LOGGER.info("{}类{}方法执行耗时{}ms",methodInvocation.getMethod().getDeclaringClass(),methodInvocation.getMethod().getName(),end-start);
        return result;
    }
}