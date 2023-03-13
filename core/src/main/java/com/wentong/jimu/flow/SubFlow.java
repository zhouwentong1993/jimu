package com.wentong.jimu.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubFlow {

    private List<String> services = new ArrayList<>();

    public SubFlow addService(String service) {
        services.add(service);
        return this;
    }

    public SubFlow addServices(String... services) {
        this.services.addAll(Arrays.asList(services));
        return this;
    }
}