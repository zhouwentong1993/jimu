package com.wentong.jimu.flow;

import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.TaskFactory;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Flow {

    private List<String> tasks = new ArrayList<>();
    private ServiceContext serviceContext;
    private String flowName;

    public Flow startFlow(@NonNull String flowName, @NonNull String service, Object input) {
        this.flowName = flowName;
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

    public String getFlowName() {
        return flowName;
    }
}
