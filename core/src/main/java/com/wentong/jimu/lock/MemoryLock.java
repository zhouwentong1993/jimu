package com.wentong.jimu.lock;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于内存的锁
 */
public class MemoryLock implements Locker, Closeable {

    private Lock lock = new ReentrantLock();

    @Override
    public boolean tryLock(String key, long timeout) {
        return lock.tryLock();
    }

    @Override
    public void unlock(String key) {

    }

    @Override
    public boolean lock(String key) {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
