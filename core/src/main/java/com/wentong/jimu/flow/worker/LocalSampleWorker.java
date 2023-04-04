package com.wentong.jimu.flow.worker;

import com.wentong.jimu.flow.task.Task;
import com.wentong.jimu.flow.task.TaskResult;

import static com.wentong.jimu.flow.FlowType.TEST;

public class LocalSampleWorker implements Worker {

    @Override
    public TaskResult execute(Task task) {
        return task.process();
    }

    @Override
    public String getFlowType() {
        return TEST;
    }

}
