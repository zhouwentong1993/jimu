package com.wentong.jimu.locker;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 基于内存的锁
 * 这里没有通过 ReentrantLock 实现，借助于信号量实现的。
 */
@Slf4j
public class MemoryLock implements Lock {

    private static final CacheLoader<String, Semaphore> LOADER = new CacheLoader<>() {
        @Override
        public Semaphore load(String key) {
            return new Semaphore(1, true);
        }
    };
    private static final LoadingCache<String, Semaphore> CACHE = CacheBuilder.newBuilder().build(LOADER);

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("LocalOnlyLock-scheduler");
    private static final ThreadFactory THREAD_FACTORY = runnable -> new Thread(THREAD_GROUP, runnable);
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1, THREAD_FACTORY);

    @Override
    public boolean tryLock(String key, long timeout, TimeUnit timeUnit) {
        try {
            return CACHE.getUnchecked(key).tryAcquire(timeout, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock(String key, long timeout, long leaseTime, TimeUnit timeUnit) {
        try {
            boolean lock = CACHE.getUnchecked(key).tryAcquire(timeout, timeUnit);
            if (lock) {
                SCHEDULER.schedule(() -> release(key), leaseTime, timeUnit);
            }
            return lock;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void release(String key) {
        synchronized (CACHE) {
            if (CACHE.getUnchecked(key).availablePermits() == 0) {
                CACHE.getUnchecked(key).release();
            }
        }
    }

    @Override
    public boolean tryLock(String key) {
        return CACHE.getUnchecked(key).tryAcquire();
    }

    @Override
    public void deleteLock(String key) {
        CACHE.invalidate(key);
    }

}
