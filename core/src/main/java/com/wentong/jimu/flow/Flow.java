package com.wentong.jimu.flow;

import cn.hutool.core.util.IdUtil;
import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.TaskFactory;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.wentong.jimu.flow.FlowType.TEST;

public class Flow {

    private final List<FlowTask> tasks = new ArrayList<>();
    private ServiceContext serviceContext;

    private String flowId;

    public Flow startFlow(@NonNull String service, Object input, boolean finalTask) {
        flowId = IdUtil.fastUUID();
        FlowTask task = TaskFactory.buildTask(service, input, this, TEST, finalTask);
        tasks.add(task);
        serviceContext = new ServiceContext();
        return this;
    }

    /**
     * next flow，用来连接那种没有终止任务的
     */
    public Flow nextFlowWithoutEndTask(String... service) {
        for (String s : service) {
            FlowTask task = TaskFactory.buildTask(s, this, TEST, false);
            tasks.add(task);
        }
        return this;
    }

    public Flow nextFlow(String service, boolean finalTask) {
        FlowTask task = TaskFactory.buildTask(service, this, TEST, finalTask);
        tasks.add(task);
        return this;
    }

    public Flow flowFinalTask(String service) {
        FlowTask task = TaskFactory.buildTask(service, this, TEST, true);
        tasks.add(task);
        return this;
    }

    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    public String getFlowId() {
        return flowId;
    }

    public List<FlowTask> getTasks() {
        return tasks;
    }

    public String getTaskType() {
        return tasks.get(0).getTaskType();
    }

}
