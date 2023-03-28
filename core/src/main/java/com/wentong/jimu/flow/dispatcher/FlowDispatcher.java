package com.wentong.jimu.flow.dispatcher;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.TaskResult;

import java.util.List;

/**
 * flow 调度器
 */
public interface FlowDispatcher {

    /**
     * 提交 flow
     */
    void submit(Flow flow);

    List<FlowTask> getTasks(String flowType, int size);

    FlowTask getTask(String flowType);

    void reportTaskResult(TaskResult taskResult);

}
