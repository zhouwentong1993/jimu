package com.wentong.jimu.flow.task;

import com.wentong.jimu.flow.dispatcher.MemoryFlowDispatcher;
import com.wentong.jimu.flow.task.poller.LocalTaskPoller;
import com.wentong.jimu.flow.task.poller.PollingExecutor;
import com.wentong.jimu.flow.task.poller.PollingSemaphore;
import com.wentong.jimu.flow.worker.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.wentong.jimu.flow.task.TaskConstant.CPU_COUNT;

public class TaskRunner {

    private final PollingExecutor pollingExecutor = new PollingExecutor(new PollingSemaphore(), new LocalTaskPoller(new MemoryFlowDispatcher()));

    private final ExecutorService executorService = Executors.newScheduledThreadPool(CPU_COUNT);

    private final List<Worker> workers = new ArrayList<>(CPU_COUNT);

    /**
     * 任务初始化
     * 里面包括一个定时任务，定时去拉取任务
     */
    public synchronized void go() {


    }

}
