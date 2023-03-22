package com.wentong.jimu.flow.task;

/**
 * Flow 子任务
 */
public interface Task {

    /**
     * 前置处理器
     */
    void before();

    /**
     * 服务处理
     */
    TaskResult process();

    /**
     * 后置处理器
     */
    void after();

    /**
     * 异常处理器
     */
    void exception(Throwable e);

    /**
     * 获取任务 id
     */
    String getId();

}
