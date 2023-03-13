package com.wentong.jimu.main;

import com.wentong.jimu.flow.FlowDef;
import com.wentong.jimu.flow.executor.DefaultFlowExecutor;
import com.wentong.jimu.flow.executor.FlowExecutor;
import com.wentong.jimu.service.ServiceFactory;
import com.wentong.jimu.service.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceFactory.registerService("ServiceA", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceB", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceC", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));

        FlowDef flowDef = new FlowDef();
        flowDef.startFlow("ServiceA", "ServiceA").nextFlow("ServiceB").nextFlow("ServiceC");

        FlowExecutor flowExecutor = new DefaultFlowExecutor(flowDef);
        flowExecutor.submit("ServiceA", "hello");

    }

}
