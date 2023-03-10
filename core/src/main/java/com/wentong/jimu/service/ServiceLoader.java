package com.wentong.jimu.service;

import java.lang.reflect.InvocationTargetException;

public class ServiceLoader {

    private final static ServiceLoader INSTANCE = new ServiceLoader();

    public static ServiceLoader getInstance() {
        return INSTANCE;
    }

    public Service<?> loadClass(String servicePath) {
        try {
            Class<?> serviceClass = Class.forName(servicePath);
            return (Service<?>) serviceClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new IllegalStateException("Service " + servicePath + " not found", e);
        }
    }
}
