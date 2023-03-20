package com.wentong.jimu.flow.executor;

import com.wentong.jimu.flow.Flow;
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
public class DefaultFlowExecutor extends ServiceThread implements LifeCycle {

    private static final int DEFAULT_THREAD_POOL_SIZE = 10000;

    private final Flow flow;

    public DefaultFlowExecutor(Flow flow) {
        this.flow = flow;
    }

    private BlockingQueue<Service<?>> queue = new ArrayBlockingQueue<>(DEFAULT_THREAD_POOL_SIZE);

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
                    // 这里的 message 是上一个任务的返回值或者是入口的参数。Context 是围绕着 flow 的设计。
                    // 应该提供给 task 更改 Context 的能力。
                    service.process(null, flow.getServiceContext());
                    service.after();
                } catch (Exception e) {
                    log.error("", e);
                    service.exception(e);
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
