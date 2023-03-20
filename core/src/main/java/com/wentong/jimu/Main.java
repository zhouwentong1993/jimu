package com.wentong.jimu;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.executor.DefaultFlowExecutor;
import com.wentong.jimu.flow.executor.FlowExecutor;
import com.wentong.jimu.service.ServiceFactory;
import com.wentong.jimu.service.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceFactory.registerService("ServiceA", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceB", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceC", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));

        Flow flow = new Flow();
        flow.startFlow("ServiceA", "ServiceA", "hello").nextFlow("ServiceB").nextFlow("ServiceC");

        FlowExecutor flowExecutor = new DefaultFlowExecutor(flow);
        flowExecutor.submit("ServiceA");

    }

}
