package com.wentong.jimu.sample;

import com.wentong.jimu.service.Service;

public class ServiceC implements Service<String> {

    @Override
    public Object process(String message) {
        System.out.println("ServiceC.process");
        return "Hello " + message;
    }
}
