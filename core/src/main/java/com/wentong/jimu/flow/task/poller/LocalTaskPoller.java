package com.wentong.jimu.flow.task.poller;

import com.wentong.jimu.flow.dispatcher.FlowDispatcher;
import com.wentong.jimu.flow.task.FlowTask;

import java.util.List;

/**
 * 通过本地方法调用 dispatcher 的方式去获取任务，仅适用于单机环境。
 */
public class LocalTaskPoller implements TaskPoller {

    private final FlowDispatcher flowDispatcher;

    public LocalTaskPoller(FlowDispatcher flowDispatcher) {
        this.flowDispatcher = flowDispatcher;
    }

    @Override
    public FlowTask poll(String flowType) {
        return flowDispatcher.getTask(flowType);
    }

    @Override
    public List<FlowTask> batchPoll(String flowType, int size) {
        return flowDispatcher.getTasks(flowType, size);
    }
}
