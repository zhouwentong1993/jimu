package com.wentong.jimu.service;

import com.wentong.jimu.flow.Flow;
import com.wentong.jimu.flow.ServiceContext;
import com.wentong.jimu.flow.dispatcher.FlowDispatcher;
import com.wentong.jimu.flow.dispatcher.MemoryFlowDispatcher;
import com.wentong.jimu.flow.task.TaskRunner;
import org.junit.Test;

public class StartupTest {

    @Test
    public void testAll() {
        ServiceFactory.registerService("ServiceA", ServiceLoader.getInstance().loadClass("com.wentong.jimu.service.ServiceA"));
        ServiceFactory.registerService("ServiceB", ServiceLoader.getInstance().loadClass("com.wentong.jimu.service.ServiceB"));
        ServiceFactory.registerService("ServiceC", ServiceLoader.getInstance().loadClass("com.wentong.jimu.service.ServiceC"));

        ServiceContext serviceContext = new ServiceContext();
        serviceContext.put("name", "wentong");
        serviceContext.put("age", 18);

        Flow flow = new Flow();
        flow.startFlow("ServiceA", "hello", serviceContext, false).nextFlow("ServiceB", false).flowFinalTask("ServiceC");
        FlowDispatcher dispatcher = new MemoryFlowDispatcher();
        dispatcher.submit(flow);

        TaskRunner taskRunner = new TaskRunner(dispatcher);
        taskRunner.go();

        // 主线程 join 在这儿
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
