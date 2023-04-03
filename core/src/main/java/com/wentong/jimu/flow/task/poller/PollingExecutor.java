package com.wentong.jimu.flow.task.poller;

import com.wentong.jimu.flow.task.Task;
import com.wentong.jimu.flow.task.TaskExecutor;
import com.wentong.jimu.flow.task.TaskResult;
import com.wentong.jimu.flow.task.reporter.TaskReporter;
import com.wentong.jimu.flow.worker.Worker;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class PollingExecutor {

    private final PollingSemaphore pollingSemaphore;
    private final TaskPoller taskPoller;
    private final TaskExecutor taskExecutor;
    private final TaskReporter taskReporter;

    public PollingExecutor(PollingSemaphore pollingSemaphore, TaskPoller taskPoller, TaskReporter taskReporter) {
        this.pollingSemaphore = pollingSemaphore;
        this.taskPoller = taskPoller;
        this.taskReporter = taskReporter;
        this.taskExecutor = new TaskExecutor();
    }

    public void getAndExecute(Worker worker) {
        if (pollingSemaphore.canPoll()) {
            try {
                Task task = taskPoller.poll(worker.getFlowType());
                log.info("polling task: {}", task);
                if (task != null) {
                    CompletableFuture<TaskResult> future = taskExecutor.submit(worker, task);
                    future.whenComplete((result, throwable) -> {
                        // 在任务中已经处理了异常，这里不需要再处理
                        log.info("task result: {}", result);
                        taskReporter.report(result);
                    });
                }
            } finally {
                pollingSemaphore.release();
            }
        }
    }

}
