package com.wentong.jimu.flow.dispatcher;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.task.Task;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 基于内存的流程调度器，维护流程状态以及任务状态。任务状态由执行器上报维护，采用 ack 机制。
 * 采用拉取的模型设计，即由 FlowExecutor 主动拉取任务，支持批量和单个拉取。
 */
@Slf4j
public class MemoryFlowDispatcher implements FlowDispatcher {

    private static final int DEFAULT_THREAD_POOL_SIZE = 10000;

    private final BlockingQueue<Task> queue = new ArrayBlockingQueue<>(DEFAULT_THREAD_POOL_SIZE);
    private final Map<String, Flow> flowMap = new HashMap<>();


    @Override
    public synchronized void submit(Flow flow) {
        flowMap.put(flow.getFlowId(), flow);
        queue.addAll(flow.getTasks());
    }

    @Override
    public List<Task> getTasks(@NonNull String flowId) {
        Flow flow = flowMap.get(flowId);
        Objects.requireNonNull(flow);

        return null;
    }

    @Override
    public Task getTask(String flowId) {
        return null;
    }

}
