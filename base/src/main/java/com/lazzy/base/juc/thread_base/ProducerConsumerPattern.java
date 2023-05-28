package com.lazzy.base.juc.thread_base;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 线程设计模式：生产者-消费者模式
 * 简介：生产者-消费者模式一般用于将生产数据的一方和消费数据的一方分割开来，将生产数据与消费数据的过程解耦开来
 * 优点:
 * 解耦：将生产者类和消费者类进行解耦，消除代码之间的依赖性，简化工作负载的管理
 * 复用：通过将生产者类和消费者类独立开来，那么可以对生产者类和消费者类进行独立的复用与扩展
 * 调整并发数：由于生产者和消费者的处理速度是不一样的，可以调整并发数，给予慢的一方多的并发数，来提高任务的处理速度
 * 异步：对于生产者和消费者来说能够各司其职，生产者只需要关心缓冲区是否还有数据，不需要等待消费者处理完；同样的对于消费者来说，
 * 也只需要关注缓冲区的内容，不需要关注生产者，通过异步的方式支持高并发，将一个耗时的流程拆成生产和消费两个阶段，这样生产者因为执行put()的时间比较短，而支持高并发
 * 支持分布式：生产者和消费者通过队列进行通讯，所以不需要运行在同一台机器上，在分布式环境中可以通过redis的list作为队列，
 * 而消费者只需要轮询队列中是否有数据。同时还能支持集群的伸缩性，当某台机器宕掉的时候，不会导致整个集群宕掉
 */
@Slf4j
public class ProducerConsumerPattern {


    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        ProducerConsumerContainer producer = new ProducerConsumerContainer(list);
        ProducerConsumerContainer consumer = new ProducerConsumerContainer(list);
        // 情况1：队列无数据，consumer等待
        new Thread(() -> {
            log.info(">>>>获取消息中>>>>");
            Integer message = consumer.take();
            log.info("接收信息：" + message);
        }).start();
        // 情况2：队列无数据，生产者存入数据数据，消费者恢复正常
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                log.info("生产者存入数据");
                producer.put(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        // 情况3：队列数据满，生产者等待消费者取出数据存入
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                log.info("生产者存入数据:" + i);
                producer.put(i);
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info(">>>>获取消息中>>>>");
            Integer message = consumer.take();
            log.info("接收信息：" + message);
        }).start();

    }

    public static class ProducerConsumerContainer {
        // 设置队列，生产者-消费者均从该队列取数据
        private final LinkedList<Integer> list;

        // 队列长度，如果达到该值最大值，则线程阻塞。
        int capacity = 10;

        public ProducerConsumerContainer(LinkedList<Integer> list){
            this.list = list;
        }

        public void put(int value) {
            synchronized (list) {
                //当容器满的时候，producer处于等待状态
                while (list.size() == capacity) {
                    log.info("容器满，等待取出，线程等待 ....");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //没有满，则继续produce
                log.info("producer--" + Thread.currentThread().getName() + "--put:" + value);
                list.add(value);
                //唤醒其他所有处于wait()的线程，包括消费者和生产者
                list.notifyAll();
            }
        }

        public Integer take() {
            int val;
            synchronized (list) {
                //如果容器中没有数据，consumer处于等待状态
                while (list.size() == 0) {
                    log.info("容器空，不可取出，线程等待 ...");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //如果有数据，继续consume
                val = list.removeFirst();
                log.info("consumer--" + Thread.currentThread().getName() + "--take:" + val);
                //唤醒其他所有处于wait()的线程，包括消费者和生产者
                //notify必须放在同步代码块里面
                list.notifyAll();
            }
            return val;
        }
    }
}