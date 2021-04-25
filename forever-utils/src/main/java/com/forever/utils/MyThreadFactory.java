package com.forever.utils;


import org.springframework.lang.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WJX
 * @date 2020/08/15 15:42
 */
public class MyThreadFactory implements ThreadFactory {

    private AtomicInteger threadId = new AtomicInteger(1);

    @Override
    public Thread newThread(@NonNull Runnable r) {
        String threadName = "FOREVER-" + threadId.incrementAndGet();
        return new Thread(r, threadName);
    }
}
