package com.lazzy.base.spingExpand;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring 类的扩展点总结
 */
public class Expand {

    /**
     * 实现ApplicationContextAware接口 获取spring容器对象
     * 使用场景：当无法通过注入获取时，可通过容器对象获取想要的bean
     */
    @Component
    public static class OwnApplicationContext implements ApplicationContextAware {
        private ApplicationContext applicationContext;
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            // 根据beanName 判断容器中是否有这个对象信息
            boolean hasBean = applicationContext.containsBean("beanName");
            if(hasBean){
                // 根据beanName获取托管给容器管理的对象
                Object bean = applicationContext.getBean("beanName");
            }
            // ...
            this.applicationContext = applicationContext;
        }

        public ApplicationContext getApplicationContext() {
            return applicationContext;
        }
    }

    /**
     * 实现ApplicationRunner接口 当程序启动时执行run方法
     * ApplicationArguments args 对象方式 程序启动参数
     */
    public static class OwnRunner implements ApplicationRunner{
        @Override
        public void run(ApplicationArguments args) throws Exception {

        }
    }

    /**
     * 实现CommandLineRunner接口 当程序启动时执行run方法
     * String... args 对象方式 程序启动参数
     * 与ApplicationRunner相比，参数不同外，效果一致
     */
    public static class OwnCommand implements CommandLineRunner {
        private final Model model;

        public OwnCommand(Model model) {
            this.model = model;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println(model.getUser());
        }
    }

    /**
     * spring 拦截器扩展 本质来说就是一个切面，真正执行目标函数前，对调用进行处理（认证、校验）
     * HandlerInterceptorAdapter 该类已过时
     * HandlerInterceptor 接口替换 用户自己实现拦截逻辑
     */
    public static class AuthInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String requestUrl = request.getRequestURI();
            return checkAuth(requestUrl);
        }

        private boolean checkAuth(String requestUrl) {
            System.out.println("===权限校验===");
            return true;
        }
    }

    /**
     * User未交给IOC容器管理 通过import导入bean由spring创建
     */
    @Import(User.class)
    @Component
    public static class Model{
        private final User user;

        public Model(User user) {
            this.user = user;
        }

        public User getUser(){
            return user;
        }
    }

    /**
     * InitializingBean 初始化bean方法 可以对容器bean进行初始化方法
     * DisposableBean 容器对象销毁时调用的方法
     */
    @Service
    @Import(User.class)
    public static class DService implements InitializingBean, DisposableBean {
        @Resource
        private OwnApplicationContext ownApplicationContext;

        @Resource
        private User user;

        @Override
        public void destroy() throws Exception {
            System.out.println("DisposableBean destroy");
            System.out.println(user.getName());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("InitializingBean afterPropertiesSet");
            User bean = ownApplicationContext.getApplicationContext().getBean(User.class);
            bean.setName("test");
        }
    }

}
