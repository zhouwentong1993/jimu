package com.wentong.jimu.test;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class CompleteFutureTest {

    @Test
    public void testWhenException() {
        // 测试 CompleteFuture 抛出异常时返回值的情况
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("test");
        });
        future.whenComplete((s, e) -> {
            System.out.println(s);
            System.out.println(e);
        });
    }

}
