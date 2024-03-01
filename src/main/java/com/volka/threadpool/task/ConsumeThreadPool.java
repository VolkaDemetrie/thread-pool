package com.volka.threadpool.task;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RequiredArgsConstructor
@Component
public class ConsumeThreadPool {

    private final TaskBlockingQueue taskBlockingQueue;

    private ExecutorService executorService;

    @PostConstruct
    private void init() {
        executorService = Executors.newFixedThreadPool(10);
    }
}
