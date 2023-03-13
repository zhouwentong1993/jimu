package com.wentong.jimu.flow.executor;

import com.wentong.jimu.flow.FlowDef;
import com.wentong.jimu.service.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 默认的流程执行器，通过线程池和队列来执行流程。
 */
public class DefaultFlowExecutor implements FlowExecutor{

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
    public void start() {

    }
}
