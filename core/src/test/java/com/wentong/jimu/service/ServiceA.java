package com.wentong.jimu.service;

import com.wentong.jimu.flow.ServiceContext;

public class ServiceA extends AbstractService<String> {

    @Override
    public Object process(String message, ServiceContext context) {
        System.out.println("ServiceA.process");
        return "Hello " + message;
    }
}
