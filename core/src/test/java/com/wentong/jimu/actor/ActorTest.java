package com.wentong.jimu.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Test;

public class ActorTest {

    @Test
    public void testActor1() {
        // 创建 actor 系统
        ActorSystem system = ActorSystem.create("HelloWorld");

        // 创建 HelloWorldActor 实例
        ActorRef helloWorldActor = system.actorOf(Props.create(HelloWorldActor.class), "HelloWorldActor");

        for (int i = 0; i < 100; i++) {

            // 发送一条消息给 HelloWorldActor
            helloWorldActor.tell("hello", ActorRef.noSender());
        }

        // 关闭 actor 系统
//        system.terminate();
    }

}
