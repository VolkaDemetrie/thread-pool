package com.volka.threadpool.executorservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;

/**
 * ExecutorService의 invokeAny(), invokeAll() 테스트
 */
public class InvokeTest {


    private List<Callable<String>> createTaskList() {
        Callable<String> firstTask = () -> {
            Thread.sleep(1000L);
            final String result = "First";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> secondTask = () -> {
            Thread.sleep(4000L);
            final String result = "Second";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> thirdTask = () -> {
            Thread.sleep(2000L);
            final String result = "Third";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> fourthTask = () -> {
            Thread.sleep(3000L);
            final String result = "Fourth";
            System.out.println("result = " + result);
            return result;
        };

        return List.of(firstTask, secondTask, thirdTask, fourthTask);
    }

    @DisplayName("invokeAll test")
    @Test
    void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Instant start = Instant.now();

        List<Future<String>> futureList = executorService.invokeAll(createTaskList());

        for (Future<String> result : futureList) {
            System.out.println(result.get());
        }

        System.out.println("time= " + Duration.between(start, Instant.now()).getSeconds());
        executorService.shutdown();
    }


    @DisplayName("invokeAny() test")
    @Test
    void invokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        String fastResult = executorService.invokeAny(createTaskList()); // 가장 빨리 처리된 결과만 반환, 나머진 cancel() 처리

        System.out.println("result= " + fastResult);
        System.out.println("time= " + Duration.between(start, Instant.now()).getSeconds());
        executorService.shutdown();

    }
}
