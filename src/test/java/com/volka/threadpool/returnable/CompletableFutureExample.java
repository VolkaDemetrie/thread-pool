package com.volka.threadpool.returnable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * CompletableFuture 예제
 */
public class CompletableFutureExample {

    @DisplayName("runAsync() example")
    @Test
    void runAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Thread :: " + Thread.currentThread().getName());
        });

        future.get();
        System.out.println("Thread :: " + Thread.currentThread().getName());
    }

    @DisplayName("supplyAsync() :: 반환값 존재")
    @Test
    void supplyAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Thread :: " + Thread.currentThread().getName();
        });


        System.out.println(future.get());
        System.out.println("Thread :: " + Thread.currentThread().getName());
    }

    @DisplayName("thenApply() 반환된 값을 추가 작업하여 반환하는 콜백")
    @Test
    void thenApply() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Thread :: " + Thread.currentThread().getName();
        }).thenApply(s -> {
            return s.toUpperCase(Locale.KOREA);
        });

        System.out.println(future.get());
    }
    @DisplayName("thenAccept() 반환된 값을 추가 작업 후 반환하지 않는 콜백")
    @Test
    void thenAccept() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Thread :: " + Thread.currentThread().getName();
        }).thenAccept(s -> {
            System.out.println(s.toUpperCase());
        });

        future.get();
    }

    @DisplayName("thenRun() 반환 값을 받지 않고 다른 작업을 실행하는 콜백")
    @Test
    void thenRun() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Thread :: " + Thread.currentThread().getName();
        }).thenRun(() -> {
            System.out.println("Thread :: " + Thread.currentThread().getName());
        });

        future.get();
    }

    @DisplayName("thenCompose() 두 작업이 이어서 실행되도록 조합. 앞선 작업의 결과를 받아서 사용 가능")
    @Test
    void thenCompose() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "HI";
        });

        CompletableFuture<String> afterFuture = future.thenCompose(this::createText);
        System.out.println(afterFuture.get());
    }

    private CompletableFuture<String> createText(String text) {
        return CompletableFuture.supplyAsync(() -> {
            return text + " " + "Async";
        });
    }

    @DisplayName("thenCombine() 각각의 작업들이 독립적으로 실행되고 얻어진 두 결과를 조합해서 작업을 처리")
    @Test
    void thenCombine() throws InterruptedException, ExecutionException {
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            return "HI";
        });

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            return "Async";
        });

        CompletableFuture<String> future = hi.thenCombine(async, (h, a) -> h + " " + a);
        System.out.println(future.get());
    }

    @DisplayName("allOf() 모든 결과 콜백 적용")
    @Test
    void allOf() throws InterruptedException, ExecutionException {
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            return "HI";
        });

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            return "Async";
        });

        List<CompletableFuture<String>> futureList = List.of(hi, async);
        CompletableFuture<List<String>> result = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]))
                .thenApply(v -> futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
                );

        result.get().forEach(System.out::println);
    }
}
