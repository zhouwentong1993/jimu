package com.wentong.jimu.flow.dispatcher;

import cn.hutool.core.collection.CollUtil;
import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.task.DefaultTask;
import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.TaskResult;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 基于内存的流程调度器，维护流程状态以及任务状态。任务状态由执行器上报维护，采用 ack 机制。
 * 采用拉取的模型设计，即由 FlowExecutor 主动拉取任务，支持批量和单个拉取。
 */
@Slf4j
public class MemoryFlowDispatcher implements FlowDispatcher {

    private final Map<String, Flow> flowMap = new HashMap<>();
    // taskType -> tasks
    private final Map<String, Deque<FlowTask>> taskTypeMap = new HashMap<>();
    // taskId -> task
    private final Map<String, FlowTask> taskMap = new HashMap<>();
    // 正在处理中的任务数据。key 为 taskId，value 为 task
    // 暂时没有用处，只是用来监控任务的执行情况。
    private final Map<String, FlowTask> processingTask = new HashMap<>();
    // flowId -> tasks 映射关系
    private final Map<String, List<FlowTask>> flowTaskMap = new HashMap<>();

    @Override
    public synchronized void submit(Flow flow) {
        if (CollUtil.isEmpty(flow.getTasks())) {
            log.warn("flow {} has no task", flow.getFlowId());
            return;
        }

        // 创建 flowId -> flow 映射
        flowMap.put(flow.getFlowId(), flow);

        // 创建 flowId -> tasks 映射
        flowTaskMap.put(flow.getFlowId(), flow.getTasks());

        flow.getTasks().forEach(task -> {
            // 加入到 id 映射
            taskMap.put(task.getId(), task);
        });
        // 将第一个任务放到执行队列中去。
        putTaskIntoQueue(flow.getTasks().get(0));
    }

    private void putTaskIntoQueue(FlowTask task) {
        Deque<FlowTask> flowTasks = taskTypeMap.get(task.getTaskType());
        if (CollUtil.isEmpty(flowTasks)) {
            flowTasks = new LinkedList<>();
            flowTasks.addFirst(task);
            taskTypeMap.put(task.getTaskType(), flowTasks);
        } else {
            flowTasks.addFirst(task);
        }
    }

    @Override
    public List<FlowTask> getTasks(@NonNull String flowType, int size) {
        Deque<FlowTask> tasks = taskTypeMap.get(flowType);
        if (!CollUtil.isEmpty(tasks)) {
            List<FlowTask> result = new ArrayList<>();
            for (int i = 0; i < Math.min(size, tasks.size()); i++) {
                FlowTask flowTask = tasks.pollFirst();
                result.add(flowTask);
                processingTask.put(flowTask.getId(), flowTask);
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public FlowTask getTask(String flowType) {
        Deque<FlowTask> tasks = taskTypeMap.get(flowType);
        FlowTask flowTask = tasks.pollFirst();
        if (flowTask != null) {
            processingTask.put(flowTask.getId(), flowTask);
            return flowTask;
        } else {
            return null;
        }
    }

    @Override
    public void reportTaskResult(TaskResult taskResult) {
        log.info("report task id: {} result: {}", taskResult.getTaskId(), taskResult);
        String taskId = taskResult.getTaskId();
        FlowTask flowTask = processingTask.remove(taskId);
        if (flowTask == null) {
            log.warn("task {} is not in processing", taskId);
            return;
        }

        switch (taskResult.getStatus()) {
            case SUCCESS -> {
                if (flowTask.finalTask()) {
                    // 如果是最后一个任务，那么就将 flow 从 flowMap 中移除
                    flowMap.remove(flowTask.getFlow().getFlowId());
                    log.info("flow {} is finished", flowTask.getFlow().getFlowId());
                    break;
                }
                // 执行成功，将下一个任务放入队列中
                // 这里强转类型了，将 input 设置了一下。
                DefaultTask nextTask = (DefaultTask) getNextTaskAndRemoveThisTask(taskId);
                if (nextTask != null) {
                    nextTask.setInput(taskResult.getOutput());
                    putTaskIntoQueue(nextTask);
                }
            }
            case FAIL ->
                // 执行失败，将任务放回队列中
                    putTaskIntoQueue(flowTask);
            default -> throw new IllegalStateException("Unexpected value: " + taskResult.getStatus());
        }
    }

    /**
     * 获取下一个任务。Attention：这个方法有副作用。会删除该任务
     */
    private FlowTask getNextTaskAndRemoveThisTask(String taskId) {
        FlowTask flowTask = taskMap.get(taskId);
        if (flowTask == null) {
            return null;
        }
        Flow flow = flowTask.getFlow();
        List<FlowTask> flowTasks = flowTaskMap.get(flow.getFlowId());
        FlowTask nextFlow = flowTasks.get(flowTasks.indexOf(flowTask) + 1);
        flowTasks.remove(flowTask);
        return nextFlow;
    }

}