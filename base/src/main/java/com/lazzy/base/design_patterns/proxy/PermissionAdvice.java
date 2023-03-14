package com.lazzy.base.design_patterns.proxy;

import com.lazzy.base.sdk.PermissionCheck;
import com.lazzy.base.web.service.Authentication;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.naming.AuthenticationException;
import java.lang.reflect.Method;

import org.springframework.expression.spel.support.StandardEvaluationContext;

@Aspect
@Component
@Order(1)
@ComponentScan(value = {"com.lazzy.base.web.service"})
public class PermissionAdvice {

    @Pointcut("@annotation(com.lazzy.base.sdk.PermissionCheck)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> aClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = aClass.getMethod(methodName, parameterTypes);
        PermissionCheck annotation = method.getAnnotation(PermissionCheck.class);
        if (ObjectUtils.isEmpty(annotation)) {
            return joinPoint.proceed();
        }
        // 获取spring el表达是值
        String value = annotation.value();
        // 获取bean名称和方法
        String[] split = value.split("\\.");
        // 使用StandardEvaluationContext
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //el 解析器
        SpelExpressionParser parser = new SpelExpressionParser();
        // 获取bean对象
        Authentication authentication = contextExpression(split[0],
                standardEvaluationContext,parser);
        assert authentication != null;
        // 解析方法字符串
        Expression expression = parser.parseExpression(split[1]);
        standardEvaluationContext.setRootObject(authentication);
        // 执行方法
        Boolean bool = expression.getValue(standardEvaluationContext, boolean.class);
        if(Boolean.FALSE.equals(bool)){
            throw new AuthenticationException("权限认证失败");
        }
        return joinPoint.proceed();
    }

    private Authentication contextExpression(String beanName,
                                             StandardEvaluationContext standardEvaluationContext
            ,SpelExpressionParser parser){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PermissionAdvice.class);

        // 需要注入一个BeanResolver来解析bean引用，此处注入 BeanFactoryResolver
        standardEvaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));
        return parser.parseExpression(beanName)
                .getValue(standardEvaluationContext, Authentication.class);
    }

}
