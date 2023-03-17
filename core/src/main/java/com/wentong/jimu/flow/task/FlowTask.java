package com.wentong.jimu.flow.task;

import com.wentong.jimu.flow.FlowContext;

public interface FlowTask extends Task{

    FlowContext getContext();

    void setContext(FlowContext context);

}
