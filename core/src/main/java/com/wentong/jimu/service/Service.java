package com.wentong.jimu.service;

public interface Service<T> {

    /**
     * 服务处理
     */
    Object process(T message);

}
