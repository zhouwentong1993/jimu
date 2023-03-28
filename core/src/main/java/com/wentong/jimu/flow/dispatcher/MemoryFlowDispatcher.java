package com.wentong.jimu.flow.dispatcher;

import cn.hutool.core.collection.CollUtil;
import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.Task;
import com.wentong.jimu.flow.task.TaskResult;
import com.wentong.jimu.flow.task.TaskStatusEnum;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
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
    // taskType -> tasks
    private final Map<String, List<FlowTask>> taskTypeMap = new HashMap<>();
    // taskId -> task
    private final Map<String, FlowTask> taskMap = new HashMap<>();
    // 正在处理中的任务数据。key 为 taskId，value 为 task
    private final Map<String, FlowTask> processingTask = new HashMap<>();


    @Override
    public synchronized void submit(Flow flow) {
        if (CollUtil.isEmpty(flow.getTasks())) {
            log.warn("flow {} has no task", flow.getFlowId());
            return;
        }
        flowMap.put(flow.getFlowId(), flow);

        flow.getTasks().forEach(task -> {
            // 加入到 id 映射
            taskMap.put(task.getId(), task);

            handleTaskType(task);
        });
    }

    private void handleTaskType(FlowTask task) {
        List<FlowTask> flowTasks = taskTypeMap.get(task.getTaskType());
        if (CollUtil.isEmpty(flowTasks)) {
            flowTasks = new ArrayList<>();
            taskTypeMap.put(task.getTaskType(), flowTasks);
        } else {
            flowTasks.add(task);
        }
    }

    @Override
    public List<FlowTask> getTasks(@NonNull String flowType, int size) {
        List<FlowTask> tasks = taskTypeMap.get(flowType);
        if (!CollUtil.isEmpty(tasks)) {
            List<FlowTask> flowTasks = tasks.subList(0, Math.min(size, tasks.size()));
            // remove from taskTypeMap
            tasks.removeAll(flowTasks);
            flowTasks.forEach(task -> processingTask.put(task.getId(), task));
            return flowTasks;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public FlowTask getTask(String flowType) {
        List<FlowTask> tasks = taskTypeMap.get(flowType);
        if (!CollUtil.isEmpty(tasks)) {
            FlowTask flowTask = tasks.get(0);
            processingTask.put(flowTask.getId(), flowTask);
            return flowTask;
        }
        return null;
    }

    @Override
    public void reportTaskResult(TaskResult taskResult) {
        String taskId = taskResult.getTaskId();
        FlowTask flowTask = processingTask.get(taskId);
        if (flowTask == null) {
            log.warn("task {} is not in processing", taskId);
            return;
        }
        // 执行失败的任务重抛回任务队列
        if (taskResult.getStatus() == TaskStatusEnum.FAIL) {
//            taskTypeMap.put(flowTask.getTaskType(), flowTask);
        }


    }

}