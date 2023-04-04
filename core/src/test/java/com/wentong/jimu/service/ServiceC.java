package com.wentong.jimu.service;

import com.wentong.jimu.flow.ServiceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceC extends AbstractService<String> {

    @Override
    public Object process(String message, ServiceContext context) {
        log.info("ServiceC.process");
        log.info("message: {}, context:{}", message, context);
        return "Hello " + message;
    }
}
