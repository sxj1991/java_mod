package com.lazzy.doublesourcejpa.aspectj;


import com.lazzy.doublesourcejpa.annotation.DataSource;
import com.lazzy.doublesourcejpa.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;


/**
 * 多数据源处理
 * 
 * @author xjsun
 */
@Aspect
@Component
public class DataSourceAspect
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.lazzy.dynamic_datas_source_jpa.annotation.DataSource)"
            + "|| @within(com.lazzy.dynamic_datas_source_jpa.annotation.DataSource)")
    public void dsPointCut()
    {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        DataSource dataSource = getDataSource(point);

        if (StringUtils.hasText(dataSource.value().name()))
        {
            //设置当前线程动态数据源
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try
        {
            return point.proceed();
        }
        finally
        {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if (Objects.nonNull(dataSource))
        {
            //获取方法上的注解
            return dataSource;
        }
        //获取类上的注解
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }
}