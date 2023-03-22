package com.wentong.jimu.metrics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingMetrics implements Metrics{

    private ThreadLocal<Long> timer = new ThreadLocal<>();

    @Override
    public void start() {
        long startTime = System.currentTimeMillis();
        log.info("start at {}", startTime);
        timer.set(startTime);
    }

    @Override
    public void stop() {
        log.info("stop");
        Long start = timer.get();
        if (start != null) {
            log.info("cost {} ms", System.currentTimeMillis() - start);
        }
        timer.remove();
    }
}
