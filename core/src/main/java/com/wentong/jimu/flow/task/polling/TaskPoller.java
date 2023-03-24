package com.wentong.jimu.flow.task.polling;

import com.wentong.jimu.flow.task.Task;

import java.util.List;

public interface TaskPoller {

    Task poll(String flowType);

    List<Task> batchPoll(String flowType);
}
