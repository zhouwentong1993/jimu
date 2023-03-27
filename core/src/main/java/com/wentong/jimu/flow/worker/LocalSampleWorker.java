package com.wentong.jimu.flow.worker;

import com.wentong.jimu.flow.task.Task;
import com.wentong.jimu.flow.task.TaskResult;

public class LocalSampleWorker implements Worker {

    @Override
    public TaskResult execute(Task task) {
        return null;
    }

    @Override
    public String getFlowType() {
        return null;
    }

}
