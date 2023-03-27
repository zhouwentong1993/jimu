package com.wentong.jimu.flow.task.reporter;

import com.wentong.jimu.flow.dispatcher.FlowDispatcher;
import com.wentong.jimu.flow.task.TaskResult;
import lombok.NonNull;

import java.util.Arrays;

public class LocalTaskReporter implements TaskReporter {

    private final FlowDispatcher flowDispatcher;

    public LocalTaskReporter(FlowDispatcher flowDispatcher) {
        this.flowDispatcher = flowDispatcher;
    }

    @Override
    public void report(@NonNull TaskResult... taskResults) {
        Arrays.stream(taskResults).forEach(flowDispatcher::reportTaskResult);
    }
}
