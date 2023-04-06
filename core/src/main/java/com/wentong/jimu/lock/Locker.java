package com.wentong.jimu.lock;

public interface Locker {

    boolean tryLock(String key, long timeout);

    void unlock(String key);

    boolean lock(String key);

}
