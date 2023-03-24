package com.wentong.jimu.flow.worker;


import com.wentong.jimu.flow.task.Task;
import com.wentong.jimu.flow.task.TaskResult;

/**
 * 任务执行者
 */
public interface Worker {

    TaskResult execute(Task task);

    String getFlowType();
}
