package com.wentong.jimu.flow.executor;

import cn.hutool.core.annotation.AnnotationUtil;
import com.wentong.jimu.flow.FlowDef;
import com.wentong.jimu.lifecycle.LifeCycle;
import com.wentong.jimu.service.Service;
import com.wentong.jimu.thread.ServiceThread;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 默认的流程执行器，通过线程池和队列来执行流程。
 */
@Slf4j
public class DefaultFlowExecutor extends ServiceThread implements FlowExecutor, LifeCycle {

    private static final int DEFAULT_THREAD_POOL_SIZE = 10000;

    private final FlowDef flowDef;

    public DefaultFlowExecutor(FlowDef flowDef) {
        this.flowDef = flowDef;
    }

    private BlockingQueue<Service<?>> queue = new ArrayBlockingQueue<>(DEFAULT_THREAD_POOL_SIZE);

    @Override
    public Object submit(String flowName) {
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
            Service<?> service = null;
            try {
                service = queue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.info("从队列中获取服务失败", e);
            }
            if (service != null) {
                try {
                    service.before();
                    service.process(null);
                    service.after();
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            waitForRunning(1);
        }
    }

    @Override
    public void doService() {

    }

    @Override
    public void stop() {

    }
}
