package com.wentong.jimu.sample;

import com.wentong.jimu.flow.ServiceContext;
import com.wentong.jimu.service.AbstractService;

public class ServiceA extends AbstractService<String> {

    @Override
    public Object process(String message, ServiceContext context) {
        System.out.println("ServiceA.process");
        return "Hello " + message;
    }
}
