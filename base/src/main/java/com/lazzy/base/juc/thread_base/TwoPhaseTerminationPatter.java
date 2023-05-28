package com.lazzy.base.juc.thread_base;
/**
 * 线程两阶段终止模式
 * 一个线程控制另一个线程的启动和终止（该模式只是优雅地关闭线程或其他长时间运行的进程的众多方法之一）
 * 例如采集信息，监控线程活动，可随时动态停止启动
 */
public class TwoPhaseTerminationPatter {
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
