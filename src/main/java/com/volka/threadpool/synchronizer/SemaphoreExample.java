package com.volka.threadpool.synchronizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * 세마포어 예제
 */
public class SemaphoreExample {
    private final Semaphore semaphore;
    private final int maxAvailable;

    public SemaphoreExample(int maxAvailable) {
        this.maxAvailable = maxAvailable;
        this.semaphore = new Semaphore(maxAvailable);
    }

    public void doSemaphoreWork() {
        System.out.println("Thread :: [" + Thread.currentThread().getName() + "] start");

        try {
            Thread.sleep(1000L);
            semaphore.acquire();
            int delay = new Random().nextInt(10) + 1;
            System.out.println("Thread :: [" + Thread.currentThread().getName() + "] sleep, delay " + delay * 300);
            Thread.sleep(delay * 300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread :: [" + Thread.currentThread().getName() + "] sleep end");
        semaphore.release();
    }
}


class SemaphoreTest {
    @DisplayName("세마포어는 스레드의 점유 갯수를 제한한다")
    @Test
    void name() throws InterruptedException {
        int availableThreadCnt = 2;
        SemaphoreExample semaphoreExample = new SemaphoreExample(availableThreadCnt);

        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(run(semaphoreExample, latch), "" + (i+1)).start();
        }

        latch.await();
    }

    Runnable run(SemaphoreExample semaphoreExample, CountDownLatch latch) {
        return () -> {
            semaphoreExample.doSemaphoreWork();
            latch.countDown();
        };
    }
}