package com.wentong.jimu.flow.task.reporter;

import com.wentong.jimu.flow.task.TaskResult;
import lombok.NonNull;

public interface TaskReporter {

    void report(@NonNull TaskResult... taskResult);

}
