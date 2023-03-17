package com.wentong.jimu.flow.task;

import cn.hutool.core.util.IdUtil;
import com.wentong.jimu.flow.FlowContext;
import com.wentong.jimu.service.Service;

public class DefaultTask implements FlowTask {

    private Object input;

    private Object output;

    private Service service;

    private FlowContext flowContext;

    public DefaultTask(Service<?> service, Object input) {
        this.service = service;
        this.input = input;
    }

    @Override
    public void before() {
        service.before();
    }

    @Override
    public Object process() {
        output = service.process(input);
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
    public FlowContext getContext() {
        return null;
    }

    @Override
    public void setContext(FlowContext context) {
        this.flowContext = context;
    }
}
