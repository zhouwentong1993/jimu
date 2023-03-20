package com.wentong.jimu.service;

import com.wentong.jimu.flow.ServiceContext;

public class ServiceC extends AbstractService<String> {

    @Override
    public Object process(String message, ServiceContext context) {
        System.out.println("ServiceC.process");
        return "Hello " + message;
    }
}
