package com.lazzy.base.juc.thread_base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
@Slf4j
public class JucBaseDemo {
    /**
     * 多线程线程基础
     * 1.线程创建-启动
     * 2.线程6种状态（新建，运行，阻塞，睡眠，等待，终止）
     * 3.线程并发：原子性（对于共享变量的操作要么全部执行，要么全部不执行，需要同步操作），
     *           有序性（线程执行不保证有序，需要同步操作），
     *           可见性（线程间声明的变量值是不可见的，需要关键字volatile声明）
     */
    public void threadDemo(){
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
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.info("main 主方法运行");
    }

    /**
     * 多线程中守护线程作用
     *
     */
    public void  daemonThread(){
        /**
         * java 虚拟机 守护线程 守护的是创建它的线程
         * 线程结束，守护线程也会结束，即便任务还在运行。
         * 即 t2线程由t1创建 main方法结束时， t1未结束 程序不会停止
         * 但如果main创建的t1线程也是守护线程，main方法结束程序就会停止。
         */
        Thread t1 = new Thread(()->{
            Thread t2 = new Thread(()->{
                while (true){
                    System.out.println("程序运行正常");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t2.setDaemon(true);
            t2.start();

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("main 线程执行完毕");
        t1.interrupt();
    }

    /**
     * 线程两阶段终止模式
     * 一个线程控制另一个线程的启动和终止（该模式只是优雅地关闭线程或其他长时间运行的进程的众多方法之一）
     * 例如采集信息，监控线程活动，可随时动态停止启动
     */
    // 采集线程
    private Thread rptThread;
    public void TwoPhaseTerminationStart(){
        rptThread = new Thread(()->{
            Thread currentThread= Thread.currentThread();
            // 采集线程开始工作
            while (true){
                if (currentThread.isInterrupted()){
                    System.out.println("采集线程工作结束...处理结束善后");
                }
                // 线程睡眠
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 异常会清空打断标记 重新打标记
                    currentThread.interrupt();
                }
            }
        });

        rptThread.start();
    }

    public void TwoPhaseTerminationStop(){
       // 关闭采集线程活动
        rptThread.interrupt();
    }

}
