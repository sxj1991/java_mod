package com.lazzy.base.design;

import com.lazzy.base.design_patterns.decorator.PenTypeDecorator;
import com.lazzy.base.design_patterns.decorator.TestType;
import com.lazzy.base.design_patterns.decorator.Type;
import com.lazzy.base.design_patterns.factory.simple.EventFactory;
import com.lazzy.base.design_patterns.factory.simple.IEvent;
import com.lazzy.base.design_patterns.factory.simple.PrintEvent;
import com.lazzy.base.design_patterns.observer.domain.ImageInfo;
import com.lazzy.base.design_patterns.observer.event.UploadImageEvent;
import com.lazzy.base.design_patterns.responsibilityChain.Handler;
import com.lazzy.base.design_patterns.responsibilityChain.LoginHandler;
import com.lazzy.base.design_patterns.responsibilityChain.PermissionHandler;
import com.lazzy.base.design_patterns.responsibilityChain.vo.LoginInfo;
import com.lazzy.base.design_patterns.singleton.SingletonThreadLocal;
import com.lazzy.base.design_patterns.state.OrderEventEnum;
import com.lazzy.base.design_patterns.state.OrderStatusEnum;
import com.lazzy.base.design_patterns.strategy.domain.Message;
import com.lazzy.base.design_patterns.strategy.factory.MessageServiceStrategyFactory;
import com.lazzy.base.design_patterns.strategy.message.MessageProcess;
import com.lazzy.base.web.controller.TokenController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * java 经典设计模式 23种测试
 * 大致分为：创建型 行为型 结构型
 */
@SpringBootTest
@Slf4j
public class DesignTests {
    /**
     * 线程单例测试
     */
    @Test
    public void single() {

        new Thread(() -> {
            //同一个线程对象相同 不同对象则不同
            SingletonThreadLocal instance1 = SingletonThreadLocal.getInstance();
            log.info("memoryAddress1:{}", instance1);
            SingletonThreadLocal instance2 = SingletonThreadLocal.getInstance();
            log.info("memoryAddress2:{}", instance2);
        }).start();

        new Thread(() -> {
            SingletonThreadLocal instance3 = SingletonThreadLocal.getInstance();
            log.info("memoryAddress3:{}", instance3);
        }).start();
    }

    /**
     * 策略设计模式测试
     */
    @Test
    public void strategy() {
        Message msg = new Message();
        msg.setMsg("linkTest");
        msg.setSendUserName("xjsun");
        // 根据枚举类型 msg实例交给不同处理器处理
        String res = MessageServiceStrategyFactory.processMsgType(MessageProcess.Link.name(), msg);
        System.out.println(res);
    }

    /**
     * 装饰器模式测试
     */
    @Test
    public void decorator() {
        Type type = new TestType();
        //获取装饰器实例 传入属于装饰的类型
        PenTypeDecorator penTypeDecorator = new PenTypeDecorator(type);
        // TestType 类型获得了增强
        System.out.println(penTypeDecorator.getName());
    }

    /**
     * 观察者模式测试
     */
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void observer() {
        ApplicationEvent event = new UploadImageEvent(ImageInfo
                .builder().imageName("hello.png").imageUrl("/opt").build());
        //注入推送者 把事件源推送 多个监听者消费同一条消息
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * 责任链模式测试
     */
    @Test
    public void chain() throws Exception {
        //添加责任链顺序
        Handler handler = new Handler.Builder()
                .addHandler(new LoginHandler())
                .addHandler(new PermissionHandler())
                .build();
        new ArrayList<>();
        //按照添加的顺序执行
        handler.doHandler(buildLogin());

    }

    private LoginInfo buildLogin(){
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName("zhangsan");
        loginInfo.setPassword("123");
        loginInfo.setPermission("admin");
        return loginInfo;
    }

    /**
     * 工厂设计模式测试
     * 简单工厂模式
     */
    @Resource
    private EventFactory eventFactory;
    @Test
    public void factory(){
        //根据class类型 创建类型
        IEvent iEvent = eventFactory.create(PrintEvent.class);
        iEvent.print();
    }

    /**
     * 状态机设计模式测试
     * 通过该模式 可以改变某个对象的状态属性，例如实现一个工作流程
     */
    @Resource
    private StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine;

    @Test
    public void state(){
        //根据StateMachineEventConfig 配置类 指定的顺序执行流程
        stateMachine.start();
        stateMachine.sendEvent(OrderEventEnum.CHECK);
        stateMachine.sendEvent(OrderEventEnum.MAKE_PRICE);
        //如果该流程不处于上下关系 则不会执行
        stateMachine.sendEvent(OrderEventEnum.FEED_BACK);
    }

    /**
     * 代理设计模式
     * 使用springboot aop库 实现动态代理
     */
    @Resource
    TokenController tokenController;
    @Test
    public void proxy(){
        System.out.println(tokenController.create().getToken());
    }



}
