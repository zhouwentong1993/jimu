package com.wentong.jimu;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.dispatcher.DefaultFlowDispatcher;
import com.wentong.jimu.flow.dispatcher.FlowDispatcher;
import com.wentong.jimu.service.ServiceFactory;
import com.wentong.jimu.service.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceFactory.registerService("ServiceA", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceB", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceC", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));

        Flow flow = new Flow();
        flow.startFlow("ServiceA", "hello").nextFlow("ServiceB").nextFlow("ServiceC");
        FlowDispatcher dispatcher = new DefaultFlowDispatcher();
        dispatcher.submit(flow);
    }

}
