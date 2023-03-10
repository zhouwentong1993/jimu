package com.wentong.jimu.flow;

/**
 * flow 任务执行器
 */
public interface FlowExecutor {

    Object execute(String flowName, Object message);

}
