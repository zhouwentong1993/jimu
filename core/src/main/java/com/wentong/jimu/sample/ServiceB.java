package com.wentong.jimu.sample;

import com.wentong.jimu.service.AbstractService;

public class ServiceB extends AbstractService<String> {
    @Override
    public Object process(String message) {
        System.out.println("ServiceB.process");
        return "Hello " + message;
    }
}
