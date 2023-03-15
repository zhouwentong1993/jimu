package com.wentong.jimu.actor;

import akka.actor.AbstractActor;

// 定义一个名为 HelloWorldActor 的 actor
public class HelloWorldActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, message -> {
            System.out.println(Thread.currentThread().getName());
            if ("hello".equals(message)) {
                System.out.println("Hello World!");
            } else {
                System.out.println("Unknown message");
            }
        }).build();
    }
}

