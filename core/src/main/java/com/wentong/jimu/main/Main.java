package com.wentong.jimu.main;

import com.wentong.jimu.service.ServiceFactory;
import com.wentong.jimu.service.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceFactory.registerService("ServiceA", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceB", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
        ServiceFactory.registerService("ServiceC", ServiceLoader.getInstance().loadClass("com.wentong.jimu.sample.ServiceA"));
    }

}
