package com.wentong.jimu.flow.worker;

import com.wentong.jimu.flow.task.Task;
import com.wentong.jimu.flow.task.TaskResult;
import com.wentong.jimu.flow.task.TaskStatusEnum;

import static com.wentong.jimu.flow.FlowType.TEST;

public class LocalSampleWorker implements Worker {

    @Override
    public TaskResult execute(Task task) {
        task.process();
        return TaskResult.builder().taskId(task.getId()).status(TaskStatusEnum.SUCCESS).build();
    }

    @Override
    public String getFlowType() {
        return TEST;
    }

}
