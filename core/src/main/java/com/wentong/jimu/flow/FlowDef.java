package com.wentong.jimu.flow;

import com.wentong.jimu.flow.task.FlowTask;
import com.wentong.jimu.flow.task.TaskFactory;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FlowDef {

    private List<String> tasks = new ArrayList<>();
    private FlowContext flowContext;
    private String flowName;

    public FlowDef startFlow(@NonNull String flowName, @NonNull String service, Object input) {
        this.flowName = flowName;
        FlowTask task = TaskFactory.buildTask(service, input);
        tasks.add(task.getId());
        flowContext = new FlowContext();
        task.setContext(flowContext);
        return this;
    }

    public FlowDef nextFlow(String... service) {
        for (String s : service) {
            FlowTask task = TaskFactory.buildTask(s);
            tasks.add(task.getId());
            task.setContext(flowContext);
        }
        return this;
    }
}
