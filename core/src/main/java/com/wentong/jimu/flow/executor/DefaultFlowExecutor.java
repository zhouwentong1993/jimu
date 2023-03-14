package com.wentong.jimu.flow.executor;

import com.wentong.jimu.flow.FlowDef;
import com.wentong.jimu.lifecycle.LifeCycle;
import com.wentong.jimu.service.Service;
import com.wentong.jimu.thread.ServiceThread;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 默认的流程执行器，通过线程池和队列来执行流程。
 */
@Slf4j
public class DefaultFlowExecutor extends ServiceThread implements FlowExecutor, LifeCycle{

    private static final int DEFAULT_THREAD_POOL_SIZE = 10000;

    private final FlowDef flowDef;

    public DefaultFlowExecutor(FlowDef flowDef) {
        this.flowDef = flowDef;
    }

    private BlockingQueue<Service<?>> queue = new ArrayBlockingQueue<>(DEFAULT_THREAD_POOL_SIZE);

    @Override
    public Object submit(String flowName, Object message) {
        return null;
    }

    @Override
    public String getServiceName() {
        return "DefaultFlowExecutor";
    }

    @Override
    public void start() {
        log.info(getServiceName() + " start");
        while (!stopped) {
            try {
                Service<?> service = queue.take();
                service.process(null);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void doService() {

    }

    @Override
    public void stop() {

    }
}
