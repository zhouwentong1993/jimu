package com.wentong.jimu.flow.task;

import com.wentong.jimu.flow.Flow;

public interface FlowTask extends Task {

    Flow getFlow();

    TaskStatusEnum getStatus();

    String getTaskType();

    boolean finalTask();

}
