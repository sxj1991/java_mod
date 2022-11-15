package com.lazzy.base.design_patterns.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine
@Slf4j
public class StateMachineEventConfig {

    // Spring StateMachine 提供了注解配置实现方式，所有 StateMachineListener
    // 接口中定义的事件都能通过注解的方式来进行配置实现。这里以连接事件为案例，
    // @OnTransition 中 source 指定原始状态，target 指定目标状态，当事件触发时将会被监听到从而调用 connect() 方法。



    @OnTransition(source = "GENERATE", target = "REVIEWED")
    public void checkEvent(){
        log.warn("---------电审事件---------");
        log.info("订单生成 ------> 已审核");
    }

    @OnTransition(source = "GENERATE", target = "FEED_BACKED")
    public void checkFailEvent(){
        log.warn("---------电审失败---------");
        log.info("订单生成 ------> 已完结");
    }

    @OnTransition(source = "REVIEWED", target = "PUBLISHED")
    public void makePriceEvent(){
        log.warn("--------定价发布----------");
        log.info("已审核 -------> 已发布");
    }


    @OnTransition(source = "PUBLISHED", target = "NOT_PAY")
    public void acceptOrderEvent(){
        log.warn("--------接单时间----------");
        log.info("已发布 ------> 待付款");
    }

    @OnTransition(source = "PUBLISHED", target = "FEED_BACKED")
    public void notPeopleAcceptEvent(){
        log.warn("---------无人接单失效---------");
        log.info("已发布 -------> 已完结");
    }

    @OnTransition(source = "NOT_PAY", target = "PAID")
    public void payOrderEvent(){
        log.warn("--------付款事件----------");
        log.info("待付款 --------> 已付款");
    }

    @OnTransition(source = "NOT_PAY", target = "FEED_BACKED")
    public void orderFailureEvent(){
        log.warn("--------接单有人支付失效----------");
        log.info("待付款 ------> 已完结");
    }

    @OnTransition(source = "PAID", target = "FEED_BACKED")
    public void feedBackEvent(){
        log.warn("--------反馈事件----------");
        log.info("已付款 -------> 已完结");
    }

}