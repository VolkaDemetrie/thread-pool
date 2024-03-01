package com.volka.threadpool.synchronizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierExample {

    @DisplayName("Barrier는 다른 스레드의 대기 상태를 기다린다.")
    @Test
    void name() throws BrokenBarrierException,InterruptedException{
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6);
        for (int i = 0 ; i < 5 ; i++) {
            new Thread(run(cyclicBarrier,i+1)).start();
        }
        cyclicBarrier.await();
        System.out.println("Main Thread End");
    }

    Runnable run(CyclicBarrier barrier,int num) {
        return () -> {
            try {
                Thread.sleep(num * 1000);
                System.out.println("Thread " + num + "await");
                barrier.await();
                System.out.println("Thread " + num + "end");
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
