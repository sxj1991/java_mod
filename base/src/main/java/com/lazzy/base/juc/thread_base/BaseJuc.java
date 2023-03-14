package com.lazzy.base.juc.thread_base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class BaseJuc {
    public static void main(String[] args) {
        // 线程基础
        // 1. 创建一个新线程，重新Runnable接口run方法
        // * 一般开启线程使用线程池，不用额外创建新线程，节约系统创建和销毁线程的开销
        Thread thread = new Thread(()-> log.info("开启Thread线程"),"新线程");
        thread.start();

        // 2. FutureTask 线程stringFutureTask.get()会阻塞,直到结果返回
        // * 如果futureTask.start() 在get()之前 则方法不会运行下去，会一直阻塞等待结果
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            log.info("开启FutureTask线程");
            return "线程执行结果";
        });
        Thread futureTask = new Thread(stringFutureTask, "FutureTask线程");
        futureTask.start();
        try {
            log.info(stringFutureTask.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("main 主方法运行");



    }
}
