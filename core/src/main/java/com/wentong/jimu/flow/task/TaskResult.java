package com.wentong.jimu.flow.task;

import lombok.Builder;
import lombok.Data;

/**
 * 任务执行结果
 */
@Data
@Builder
public class TaskResult {

    private Object input;
    private Object output;
    private Throwable throwable;
    private String executionId;
    private TaskStatusEnum status;
    private String taskId;

}
