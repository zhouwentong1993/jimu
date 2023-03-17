package com.wentong.jimu.service;

public abstract class AbstractService<T> implements Service<T> {

    @Override
    public void before() {
        // do nothing
    }

    @Override
    public void after() {
        // do nothing
    }

    @Override
    public void exception(Throwable e) {
        // do nothing
    }
}
