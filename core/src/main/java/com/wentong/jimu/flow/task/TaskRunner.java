package com.wentong.jimu.flow.task;

import com.wentong.jimu.flow.dispatcher.FlowDispatcher;
import com.wentong.jimu.flow.task.poller.LocalTaskPoller;
import com.wentong.jimu.flow.task.poller.PollingExecutor;
import com.wentong.jimu.flow.task.poller.PollingSemaphore;
import com.wentong.jimu.flow.task.reporter.LocalTaskReporter;
import com.wentong.jimu.flow.worker.LocalSampleWorker;
import com.wentong.jimu.flow.worker.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.wentong.jimu.flow.task.TaskConstant.CPU_COUNT;

public class TaskRunner {

    private final PollingExecutor pollingExecutor;
    private final List<Worker> workers = new ArrayList<>();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(CPU_COUNT);

    public TaskRunner(FlowDispatcher flowDispatcher) {
        pollingExecutor = new PollingExecutor(new PollingSemaphore(), new LocalTaskPoller(flowDispatcher), new LocalTaskReporter(flowDispatcher));
        for (int i = 0; i < CPU_COUNT; i++) {
            workers.add(new LocalSampleWorker());
        }
    }

    /**
     * 任务初始化
     * 里面包括一个定时任务，定时去拉取任务
     */
    public synchronized void go() {
        workers.forEach(worker -> scheduledExecutorService.scheduleWithFixedDelay(() -> pollingExecutor.getAndExecute(worker), 0, 2, TimeUnit.SECONDS));
    }
}
