package com.wentong.jimu.sample;

import com.wentong.jimu.service.Service;

public class ServiceA implements Service<String> {

    @Override
    public Object process(String message) {
        System.out.println("ServiceA.process");
        return "Hello " + message;
    }
}
