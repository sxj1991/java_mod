package com.lazzy.base.designPatterns.state;

/**
 * 订单事件枚举
 */
public enum OrderEventEnum {
    /**
     * 电审
     */
    CHECK,

    /**
     * 电审失败:
     */
    CHECK_FAIL,

    /**
     * 定价发布
     */
    MAKE_PRICE,

    /**
     * 接单
     */
    ACCEPT_ORDER,

    /**
     * 无人接单失效
     */
    NOT_PEOPLE_ACCEPT,

    /**
     * 付款
     */
    PAY_ORDER,

    /**
     * 接单有人支付失效
     */
    ORDER_FAILURE,

    /**
     * 反馈
     */
    FEED_BACK;

}