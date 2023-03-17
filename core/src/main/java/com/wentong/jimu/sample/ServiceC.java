package com.wentong.jimu.sample;

import com.wentong.jimu.service.AbstractService;

public class ServiceC extends AbstractService<String> {

    @Override
    public Object process(String message) {
        System.out.println("ServiceC.process");
        return "Hello " + message;
    }
}
