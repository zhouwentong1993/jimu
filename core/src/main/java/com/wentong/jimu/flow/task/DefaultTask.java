package com.wentong.jimu.flow.task;

import cn.hutool.core.util.IdUtil;
import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.metrics.LoggingMetrics;
import com.wentong.jimu.metrics.Metrics;
import com.wentong.jimu.service.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的 Task
 */
@Slf4j
public class DefaultTask implements FlowTask {

    private final Object input;

    private final Service service;

    private final Flow flow;

    private final String id;

    private final Metrics metrics;

    private final String flowType;

    private final boolean finalTask;

    public DefaultTask(Service<?> service, Object input, Flow flow, String flowType, boolean finalTask) {
        this.service = service;
        this.input = input;
        this.flow = flow;
        this.flowType = flowType;
        this.finalTask = finalTask;
        this.id = IdUtil.fastUUID();
        metrics = new LoggingMetrics();
    }

    @Override
    public void before() {
        service.before();
    }

    @Override
    public TaskResult process() {
        String executionId = IdUtil.fastUUID();
        log.info("执行 id：{}", executionId);
        metrics.start();
        Object output = service.process(input, getFlow().getServiceContext());
        metrics.stop();
        return TaskResult.builder().input(input).output(output).executionId(IdUtil.fastUUID()).taskId(getId()).status(TaskStatusEnum.SUCCESS).build();
    }

    @Override
    public void after() {
        service.after();
    }

    @Override
    public void exception(Throwable e) {
        service.exception(e);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Flow getFlow() {
        return this.flow;
    }

    @Override
    public TaskStatusEnum getStatus() {
        return null;
    }

    @Override
    public String getTaskType() {
        return flowType;
    }

    @Override
    public boolean finalTask() {
        return finalTask;
    }

    @Override
    public String toString() {
        return "DefaultTask{" + "input=" + input + ", service=" + service + ", flow=" + flow + ", id='" + id + '\'' + ", metrics=" + metrics + ", flowType='" + flowType + '\'' + '}';
    }
}
