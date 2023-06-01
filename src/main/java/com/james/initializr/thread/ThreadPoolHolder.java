package com.james.initializr.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadPoolHolder {
    public static ExecutorService THREAD_POOL = new ThreadPoolExecutor(
            5,
            10,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(100),
            new MyThread(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    static class MyThread implements ThreadFactory {
        private final AtomicInteger threadCount = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    log.info("---->>> thread[{}] occur exception:{}", t.getName(), e);
                }
            });
            t.setName("crm-thread-pool-" + threadCount.getAndIncrement());
            return t;
        }
    }

    public static void run(Runnable runnable) {
        THREAD_POOL.execute(runnable);
    }

}