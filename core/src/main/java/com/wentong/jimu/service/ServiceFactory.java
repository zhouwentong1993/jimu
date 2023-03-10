package com.wentong.jimu.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceFactory {

    private static final Map<String, Service<?>> CONTAINER = new HashMap<>();

    public static void registerService(String serviceName, Service<?> service) {
        CONTAINER.put(serviceName, service);
    }

    public static Service<?> getService(String serviceName) {
        Service<?> service = CONTAINER.get(serviceName);
        Objects.requireNonNull(service, "service:" + serviceName + " is null");
        return service;
    }

}
