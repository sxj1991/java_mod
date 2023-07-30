package com.lazzy.base.juc.thread_pool;

import java.util.concurrent.*;

public final class WorkFactory {
    private static final ExecutorService executor = new ThreadPoolExecutor(4,
            20,200, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());

    private WorkFactory(){}

    public static void work(Runnable task){
        executor.execute(task);
    }

    public static <T> Future<T> work(Callable<T> task){
        return executor.submit(task);
    }


}
