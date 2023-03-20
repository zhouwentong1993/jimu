package com.wentong.jimu.flow.task;

import cn.hutool.core.util.IdUtil;
import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.service.Service;

public class DefaultTask implements FlowTask {

    private Object input;

    private Object output;

    private Service service;

    private final Flow flow;

    public DefaultTask(Service<?> service, Object input, Flow flow) {
        this.service = service;
        this.input = input;
        this.flow = flow;
    }

    @Override
    public void before() {
        service.before();
    }

    @Override
    public Object process() {
        output = service.process(input, getFlow().getServiceContext());
        return output;
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
    public String getName() {
        return service.getClass().getSimpleName();
    }

    @Override
    public String getId() {
        return IdUtil.fastUUID();
    }

    @Override
    public Flow getFlow() {
        return this.flow;
    }
}
