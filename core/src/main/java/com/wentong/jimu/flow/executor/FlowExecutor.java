package com.wentong.jimu.flow.executor;

/**
 * flow 任务执行器
 */
public interface FlowExecutor {

    Object submit(String flowName);
}
