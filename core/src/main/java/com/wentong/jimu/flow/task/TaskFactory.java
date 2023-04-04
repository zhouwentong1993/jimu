package com.wentong.jimu.flow.task;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.service.ServiceFactory;

public class TaskFactory {

    public static FlowTask buildTask(String service, Object input, Flow flow, String flowType, boolean finalTask) {
        return new DefaultTask(ServiceFactory.getService(service), input, flow, flowType, finalTask);
    }

    public static FlowTask buildTask(String service, Flow flow, String flowType, boolean finalTask) {
        return new DefaultTask(ServiceFactory.getService(service), null, flow, flowType, finalTask);
    }

}
