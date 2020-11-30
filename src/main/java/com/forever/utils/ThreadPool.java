package com.forever.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author WJX
 * @date 2020/08/15 10:16
 */
public class ThreadPool {

    private static final Double TASK_TIME = 0.1;

    private static final Double RESPONSE_TIME = 1.0;

    private static final Integer MAX_TASK = 500;


    private static ThreadPoolExecutor threadPool;

    static {

        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;

        int queueCapacity = (int) (corePoolSize / TASK_TIME * RESPONSE_TIME);

        int maximumPoolSize = (int) ((MAX_TASK - queueCapacity) / (1 / TASK_TIME));

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity), new MyThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }


    public static ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }


}
