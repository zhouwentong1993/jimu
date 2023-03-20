package com.wentong.jimu.sample;

import com.wentong.jimu.flow.ServiceContext;
import com.wentong.jimu.service.AbstractService;

public class ServiceC extends AbstractService<String> {

    @Override
    public Object process(String message, ServiceContext context) {
        System.out.println("ServiceC.process");
        return "Hello " + message;
    }
}
