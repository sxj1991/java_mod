package com.lazzy.doublesourcejpa.aspectj;


import com.lazzy.doublesourcejpa.annotation.DataSource;
import com.lazzy.doublesourcejpa.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;


/**
 * 多数据源处理
 *
 * @author xjsun
 */
@Aspect
@Component
public class DataSourceAspect {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("@annotation(com.lazzy.doublesourcejpa.annotation.DataSource)"
            + "|| @within(com.lazzy.doublesourcejpa.annotation.DataSource)")
    public void dsPointCut() {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSource dataSource = getDataSource(point);

        if (StringUtils.hasText(dataSource.value().name())) {
            //设置当前线程动态数据源
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try {
            return point.proceed();
        } finally {
            // Jpa不会自动断开连接 需要人工关闭session 然后会自动获取session
            // SessionImplementor session = entityManager.unwrap(SessionImplementor.class);
            // session.disconnect();
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if (Objects.nonNull(dataSource)) {
            //获取方法上的注解
            return dataSource;
        }
        //获取类上的注解
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }
}