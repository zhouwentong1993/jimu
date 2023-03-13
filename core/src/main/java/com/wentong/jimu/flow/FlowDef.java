package com.wentong.jimu.flow;

import java.util.HashMap;
import java.util.Map;

public class FlowDef {

    private final Map<String, SubFlow> flowMap = new HashMap<>();
    private SubFlow subFlow;

    public FlowDef startFlow(String flowName, String service) {
        subFlow = new SubFlow();
        subFlow.addService(service);
        flowMap.put(flowName, subFlow);
        return this;
    }

    public FlowDef nextFlow(String... service) {
        subFlow.addServices(service);
        return this;
    }
}
