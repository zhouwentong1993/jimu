package com.wentong.jimu.flow;

import cn.hutool.core.util.IdUtil;
import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.Task;
import com.wentong.jimu.flow.task.TaskFactory;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.wentong.jimu.flow.FlowType.TEST;

public class Flow {

    private final List<Task> tasks = new ArrayList<>();
    private ServiceContext serviceContext;

    private String flowId;

    public Flow startFlow(@NonNull String service, Object input) {
        flowId = IdUtil.fastUUID();
        FlowTask task = TaskFactory.buildTask(service, input, this, TEST);
        tasks.add(task);
        serviceContext = new ServiceContext();
        return this;
    }

    public Flow nextFlow(String... service) {
        for (String s : service) {
            FlowTask task = TaskFactory.buildTask(s, this, TEST);
            tasks.add(task);
        }
        return this;
    }

    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    public String getFlowId() {
        return flowId;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
