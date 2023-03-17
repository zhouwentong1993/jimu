package com.wentong.jimu.service;

public interface Service<T> {

    /**
     * 前置处理器
     */
    void before();

    /**
     * 服务处理
     */
    Object process(T message);

    /**
     * 后置处理器
     */
    void after();

    /**
     * 异常处理器
     */
    void exception(Throwable e);

}
