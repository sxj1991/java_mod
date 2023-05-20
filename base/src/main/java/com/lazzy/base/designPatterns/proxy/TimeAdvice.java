package com.lazzy.base.designPatterns.proxy;

import com.lazzy.base.sdk.LogExcute;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * 该类无法用作stater拦截使用
 *  public void afterPropertiesSet() throws Exception {
 *      this.pointcut = new AnnotationMatchingPointcut(null, LogExcute.class);
 *      this.advice = new LogAdvice(); --> TimeAdvice 会引发类型转化报错
 *  }
 */
@Aspect
@Component
public class TimeAdvice {
    private static final Logger LOGGER =(Logger) LoggerFactory.getLogger(TimeAdvice.class);
    /** 切点 */
    @Pointcut("@annotation(com.lazzy.base.sdk.LogExcute)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        Class<?> aClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("{}类-{}方法开始结算执行耗时", aClass.getName(), methodName);
        try {
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
            Method method = aClass.getMethod(methodName, parameterTypes);
            LogExcute annotation = method.getAnnotation(LogExcute.class);
            if (ObjectUtils.isEmpty(annotation)) {
                return joinPoint.proceed();
            }
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            //打印执行日志
            LOGGER.info("{}类{}方法执行耗时{}ms", aClass.getName(), methodName, end - start);
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
}
