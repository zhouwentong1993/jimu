package com.wentong.jimu.flow;

import com.wentong.jimu.exception.FlowBizException;
import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.TaskFactory;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flow {

    private final List<String> tasks = new ArrayList<>();
    private ServiceContext serviceContext;
    private final Set<String> flowIds = new HashSet<>();
    private String flowId;

    public Flow startFlow(@NonNull String flowId, @NonNull String service, Object input) {
        if (flowIds.contains(flowId)) {
            throw new FlowBizException("flowId is duplicate, flowId: " + flowId);
        }
        flowIds.add(flowId);
        this.flowId = flowId;
        FlowTask task = TaskFactory.buildTask(service, input, this);
        tasks.add(task.getId());
        serviceContext = new ServiceContext();
        return this;
    }

    public Flow nextFlow(String... service) {
        for (String s : service) {
            FlowTask task = TaskFactory.buildTask(s, this);
            tasks.add(task.getId());
        }
        return this;
    }

    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    public String getFlowId() {
        return flowId;
    }
}
