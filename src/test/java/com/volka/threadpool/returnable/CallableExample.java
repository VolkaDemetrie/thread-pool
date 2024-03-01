package com.volka.threadpool.returnable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;


public class CallableExample {

    @DisplayName("Callable 반환 없는 경우")
    @Test
    public void noReturnCallable() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                final String result = Thread.currentThread().getName();
                System.out.println("result :: " + result);
                return null;
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }


    @DisplayName("Callable 반환 있는 경우")
    @Test
    public void returnCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        System.out.println("start :: " + System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                final String result = Thread.currentThread().getName() + " returnable";
                Thread.sleep(2000L);
                return result;
            }
        };

        Future<String> result = executorService.submit(callable);
        System.out.println("result :: " + result.get()); //future의 get()은 블로킹 방식으로 동작한다.

        System.out.println("end :: " + System.currentTimeMillis());

        executorService.shutdown();
    }
}
