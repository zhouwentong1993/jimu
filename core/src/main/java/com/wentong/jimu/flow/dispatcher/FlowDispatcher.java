package com.wentong.jimu.flow.dispatcher;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.task.Task;

import java.util.List;

/**
 * flow 调度器
 */
public interface FlowDispatcher {

    /**
     * 提交 flow
     */
    void submit(Flow flow);

    List<Task> getTasks(String flowId);

    Task getTask(String flowId);
}
