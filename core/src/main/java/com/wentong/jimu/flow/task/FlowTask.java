package com.wentong.jimu.flow.task;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.TaskStatusEnum;

public interface FlowTask extends Task{

    Flow getFlow();

    TaskStatusEnum getStatus();

}
