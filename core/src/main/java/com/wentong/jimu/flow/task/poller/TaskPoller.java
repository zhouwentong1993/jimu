package com.wentong.jimu.flow.task.poller;

import com.wentong.jimu.flow.task.FlowTask;

import java.util.List;

public interface TaskPoller {

    FlowTask poll(String flowType);

    List<FlowTask> batchPoll(String flowType, int size);
}
