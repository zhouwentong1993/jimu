package com.wentong.jimu.locker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class MemoryLockTest {

    @Test
    void tryLock() {
        Lock lock = new MemoryLock();
        Assertions.assertTrue(lock.tryLock("key1"));
        boolean lock1 = lock.tryLock("key1", 100, TimeUnit.MILLISECONDS);
        Assertions.assertFalse(lock1);
        lock.release("key1");
        boolean lock2 = lock.tryLock("key1");
        Assertions.assertTrue(lock2);
    }

    @Test
    void testTryLockAndDeleteKey() {
        Lock lock = new MemoryLock();
        Assertions.assertTrue(lock.tryLock("key2", 1000, TimeUnit.SECONDS));
        Assertions.assertFalse(lock.tryLock("key2", 10, TimeUnit.MILLISECONDS));
        lock.deleteLock("key2");
        Assertions.assertTrue(lock.tryLock("key2", 10, TimeUnit.MILLISECONDS));

    }

    @Test
    void lease() throws Exception {
        Lock lock = new MemoryLock();
        lock.tryLock("key3", 2, 1, TimeUnit.SECONDS);
        Assertions.assertFalse(lock.tryLock("key3", 10, TimeUnit.MILLISECONDS));
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertTrue(lock.tryLock("key3", 10, TimeUnit.MILLISECONDS));

    }

    @Test
    void lock() {
    }

    @Test
    void deleteLock() {
    }
}