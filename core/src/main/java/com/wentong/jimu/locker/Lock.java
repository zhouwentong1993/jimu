package com.wentong.jimu.locker;

import java.util.concurrent.TimeUnit;

public interface Lock {

    /**
     * Try to lock the key.
     * @param key key to try
     * @param timeout try timeout
     * @param timeUnit timeunit
     * @return true means lock success, false means lock failed.
     */
    boolean tryLock(String key, long timeout, TimeUnit timeUnit);

    /**
     * Try to lock the key with lease time.
     * @param key key to try
     * @param timeout try timeout
     * @param leaseTime key to lease
     * @param timeUnit timeunit
     * @return true means lock success, false means lock failed.
     */
    boolean tryLock(String key, long timeout, long leaseTime, TimeUnit timeUnit);

    void release(String key);

    boolean tryLock(String key);

    /**
     * Delete the lock.
     * @param key key to delete
     */
    void deleteLock(String key);

}
