package com.lazzy.base.design_patterns.responsibilityChain;

import cn.hutool.core.util.ObjectUtil;
import com.lazzy.base.design_patterns.responsibilityChain.vo.LoginInfo;


/**
 * 单向责任链模式实现
 *
 * @param <T>
 */
public abstract class Handler<T> {

    protected Handler<T> next;

    /**
     * 服务执行的方法
     * 不同服务通过内部类 addHandler 组成一条链表按顺序执行
     *
     * @param loginInfo 登录参数
     * @return 返回结果信息
     * @throws Exception
     */
    public abstract String doHandler(LoginInfo loginInfo) throws Exception;

    public void next(Handler handler) {
        this.next = handler;
    }

    /**
     * 内部类构建责任链表
     *
     * @param <T>
     */
    public static class Builder<T> {
        private Handler<T> head;
        private Handler<T> tail;

        public  Builder<T> addHandler(Handler handler) {
            if (ObjectUtil.isEmpty(this.head)) {
                this.head = handler;
                this.tail = this.head;
            }else{
                this.tail.next(handler);
                this.tail = handler;
            }

            return this;
        }

        public Handler build() {
            return this.head;
        }
    }
}
