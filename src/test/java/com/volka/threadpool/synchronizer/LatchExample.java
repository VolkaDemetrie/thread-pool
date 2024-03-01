package com.volka.threadpool.synchronizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 예제
 */
public class LatchExample {
    @DisplayName("await 호출 쓰레드는 대기한다.")
    @Test
    public void name() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);

        new Thread(job(latch,1)).start();
        new Thread(job(latch,2)).start();
        new Thread(job(latch,3)).start();
        new Thread(job(latch,4)).start();
        System.out.println("=======Main Thread await========");
        latch.await();
        System.out.println("=======Main Thread restart========");
    }
    Runnable job(CountDownLatch latch, int num) {
        return () -> {
            try {
                Thread.sleep(num*500L);
                System.out.println("Thread Num : "+ num + "countDown");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
