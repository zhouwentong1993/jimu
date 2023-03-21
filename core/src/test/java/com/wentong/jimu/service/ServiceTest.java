package com.wentong.jimu.service;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.dispatcher.MemoryFlowDispatcher;
import com.wentong.jimu.flow.dispatcher.FlowDispatcher;
import org.junit.Test;

public class ServiceTest {

    @Test
    public void testBasic() {
        ServiceFactory.registerService("ServiceA", ServiceLoader.getInstance().loadClass("com.wentong.jimu.service.ServiceA"));
        ServiceFactory.registerService("ServiceB", ServiceLoader.getInstance().loadClass("com.wentong.jimu.service.ServiceA"));
        ServiceFactory.registerService("ServiceC", ServiceLoader.getInstance().loadClass("com.wentong.jimu.service.ServiceA"));

        Flow flow = new Flow();
        flow.startFlow("ServiceA", "hello").nextFlow("ServiceB").nextFlow("ServiceC");
        FlowDispatcher dispatcher = new MemoryFlowDispatcher();
        dispatcher.submit(flow);
    }

}
