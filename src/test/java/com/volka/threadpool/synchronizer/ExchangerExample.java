package com.volka.threadpool.synchronizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

public class ExchangerExample {

    @DisplayName("Exchanger는 쓰레드간 객체를 교환한다")
    @Test
    public void name() throws InterruptedException {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();
        CountDownLatch latch = new CountDownLatch(2);

        Thread addThread = new Thread(adder(exchanger, latch), "adder");
        Thread removeThread = new Thread(remover(exchanger, latch), "remover");

        addThread.start();
        removeThread.start();
        latch.await();
    }

    Runnable adder(Exchanger<List<Integer>> exchanger, CountDownLatch latch) {
        return () -> {
            int num = 0;
            List<Integer> emptyList = new ArrayList<>();

            while (num != 2) {
                try {
                    int size = 0;
                    while (emptyList.size() < 8) {
                        Thread.sleep(500L);
                        emptyList.add(size);
                        System.out.println(Thread.currentThread().getName() + " :: add " + size);
                        size++;
                    }

                    System.out.println("emptyList is Full");
                    emptyList = exchanger.exchange(emptyList);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                num++;
            }

            System.out.println("emptyList Size :: " + emptyList.size());
            latch.countDown();
        };
    }


    Runnable remover(Exchanger<List<Integer>> exchanger, CountDownLatch latch) {
        return () -> {
            int num = 0;
            List<Integer> fullList = new ArrayList<>();

            while (num != 2) {
                try {
                    while (!fullList.isEmpty()) {
                        Thread.sleep(500L);
                        int removed = fullList.get(0);
                        fullList.remove(0);
                        System.out.println(Thread.currentThread().getName() + " :: remove " + removed);
                    }
                    System.out.println("fullList is empty");
                    fullList = exchanger.exchange(fullList);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num++;
            }
            System.out.println("fullList size :: " + fullList.size());
            latch.countDown();
        };
    }
}
