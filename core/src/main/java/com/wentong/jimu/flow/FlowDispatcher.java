package com.wentong.jimu.flow;

/**
 * flow 调度器
 */
public interface FlowDispatcher {

    FlowDef getNextFlow(String flowName);

}
