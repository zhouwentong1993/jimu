package com.wentong.jimu.flow.task.poller;

import java.util.concurrent.Semaphore;

/**
 * 用来控制任务拉取的信号量
 */
public class PollingSemaphore {

    private static final int DEFAULT_PERMITS = Runtime.getRuntime().availableProcessors() * 10;

    private final Semaphore semaphore;

    public PollingSemaphore() {
        semaphore = new Semaphore(DEFAULT_PERMITS);
    }

    public boolean canPoll() {
        return semaphore.tryAcquire();
    }

    public void release() {
        semaphore.release();
    }
}
