package com.lazzy.base.designPatterns.state;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

/**
 * 状态机配置类
 * 开启状态机
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatusEnum, OrderEventEnum> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderEventEnum> states)
            throws Exception {
        states
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }


    /**
     * 我们需要初始化状态机的状态。其中，initial(OrderStatusEnum.UNCONNECTED) 定义了初始状态是未连接状态
     * 。states(EnumSet.allOf(OrderStatusEnum.class)) 定义了状态机中存在的所有状态。
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderEventEnum> states) throws Exception {
        states.withStates()
                // 定义初始状态
                .initial(OrderStatusEnum.GENERATE)
                // 定义状态机状态
                .states(EnumSet.allOf(OrderStatusEnum.class));
    }

    /**
     * 我们需要初始化当前状态机有哪些状态事件。其中， source 指定原始状态，target 指定目标状态，event 指定触发事件。
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderEventEnum> transitions)
            throws Exception {

        transitions.withExternal()
                // 1.电审事件
                // 订单生成 -> 已审核
                .source(OrderStatusEnum.GENERATE)
                .target(OrderStatusEnum.REVIEWED)
                .event(OrderEventEnum.CHECK)
                .and()
                // 订单生成 -> 已完结
                .withExternal()
                .source(OrderStatusEnum.GENERATE)
                .target(OrderStatusEnum.FEED_BACKED)
                .event(OrderEventEnum.CHECK_FAIL)
                .and()

                // 2.定价发布事件
                // 已审核 -> 已发布
                .withExternal()
                .source(OrderStatusEnum.REVIEWED)
                .target(OrderStatusEnum.PUBLISHED)
                .event(OrderEventEnum.MAKE_PRICE)
                .and()

                // 3.接单事件
                .withExternal()
                // 已发布 -> 待付款
                .source(OrderStatusEnum.PUBLISHED)
                .target(OrderStatusEnum.NOT_PAY)
                .event(OrderEventEnum.ACCEPT_ORDER)
                .and()
                // 已发布 -> 已完结 2小时失效事件
                .withExternal()
                .source(OrderStatusEnum.PUBLISHED)
                .target(OrderStatusEnum.FEED_BACKED)
                .event(OrderEventEnum.NOT_PEOPLE_ACCEPT)
                .and()

                // 4.付款事件
                // 待付款 -> 已付款
                .withExternal()
                .source(OrderStatusEnum.NOT_PAY)
                .target(OrderStatusEnum.PAID)
                .event(OrderEventEnum.PAY_ORDER)
                .and()

                // 5.付款失效事件
                // 待付款 -> 已完结
                .withExternal()
                .source(OrderStatusEnum.NOT_PAY)
                .target(OrderStatusEnum.FEED_BACKED)
                .event(OrderEventEnum.ORDER_FAILURE)
                .and()

                //6.反馈事件
                // 已付款 -> 已完结
                .withExternal()
                .source(OrderStatusEnum.PAID)
                .target(OrderStatusEnum.FEED_BACKED)
                .event(OrderEventEnum.FEED_BACK);

    }

    @Bean
    public StateMachineListener<OrderStatusEnum, OrderEventEnum> listener() {
        return new StateMachineListenerAdapter<OrderStatusEnum,  OrderEventEnum>() {
            @Override
            public void stateChanged(State<OrderStatusEnum,  OrderEventEnum> from, State<OrderStatusEnum,  OrderEventEnum> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
