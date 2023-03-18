package com.wentong.jimu.sample;

import com.wentong.jimu.flow.convert.InputConverter;
import com.wentong.jimu.service.AbstractService;

public class ServiceA extends AbstractService<String> {

    @Override
    @InputConverter(expression = "message")
    public Object process(String message) {
        System.out.println("ServiceA.process");
        return "Hello " + message;
    }
}
