package com.wentong.jimu.service;

import com.wentong.jimu.flow.ServiceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceB extends AbstractService<String> {
    @Override
    public Object process(String message, ServiceContext context) {
        log.info("ServiceB.process");
        log.info("message: {}, context:{}", message, context);
        return "Hello " + context.get("name");
    }
}
