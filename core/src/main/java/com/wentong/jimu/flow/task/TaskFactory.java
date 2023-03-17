package com.wentong.jimu.flow.task;

import com.wentong.jimu.service.ServiceFactory;

public class TaskFactory {

    public static FlowTask buildTask(String service, Object input) {
        return new DefaultTask(ServiceFactory.getService(service), input);
    }

    public static FlowTask buildTask(String service) {
        return new DefaultTask(ServiceFactory.getService(service), null);
    }

}
