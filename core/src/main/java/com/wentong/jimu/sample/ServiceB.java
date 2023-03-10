package com.wentong.jimu.sample;

import com.wentong.jimu.service.Service;

public class ServiceB implements Service<String> {

    @Override
    public Object process(String message) {
        System.out.println("ServiceB.process");
        return "Hello " + message;
    }
}
