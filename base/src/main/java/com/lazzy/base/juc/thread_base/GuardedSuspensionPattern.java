package com.lazzy.base.juc.thread_base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 线程设计模式:保护性暂停
 * 线程间通信模型，Future 和 Promise 的实现原理
 * 简单来说，即一个线程等待另一个线程完成任务，在继续执行。
 */
@Slf4j
public class GuardedSuspensionPattern {

    public static void main(String[] args) {
        GuardedSuspension guardedSuspension = new GuardedSuspension();
        new Thread(() -> {
            log.info(">>>>获取消息中>>>>");
            String message = guardedSuspension.getList(2000L);
            log.info("接收信息：" + message);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            guardedSuspension.setList("发送消息：开启保护性暂停通行模式测试");
        }).start();
    }

    public static class GuardedSuspension {
        private final List<String> list = new LinkedList<>();

        /**
         * 获取队列元素
         *
         * @return string 队列最后一个元素
         */
        public String getList() {
            // 防止虚假唤醒 如果无信息 线程继续等待
            while (CollectionUtils.isEmpty(list)) {
                synchronized (list) {
                    log.info(">>>>消息为空，线程等待>>>>");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            log.info(">>>>获取消息>>>>");
            int index = list.size() - 1;
            return list.remove(index);
        }
        /**
         * 获取队列元素 设置超时时间
         *
         * @return string 队列最后一个元素
         */
        public String getList(Long timeout) {
            // 防止虚假唤醒 如果无信息 线程继续等待
            long begin = System.currentTimeMillis();
            long passedTime = 0;
            while (CollectionUtils.isEmpty(list)) {
                synchronized (list) {
                    long wait = timeout - passedTime;
                    if (wait <= 0) {
                        log.info(">>>>超时，退出线程>>>>");
                        break;
                    }
                    log.info(">>>>消息为空，线程等待>>>>");
                    try {
                        list.wait(wait);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    long end = System.currentTimeMillis();
                    passedTime = end - begin;
                }
            }
            if(CollectionUtils.isEmpty(list)){
                return "队列超时，退出";
            }
            log.info(">>>>获取消息>>>>");
            int index = list.size() - 1;
            return list.remove(index);
        }

        /**
         * 向队列头添加元素
         *
         * @param element 设置元素
         */
        public void setList(String element) {
            synchronized (list) {
                // 添加元素
                list.add(element);
                // 唤醒线程
                list.notifyAll();
            }
        }
    }
}
