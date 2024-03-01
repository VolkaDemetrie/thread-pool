package com.volka.threadpool.synchronizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * phaser 예제
 */
public class PhaserExample {

    Runnable run(Phaser ph, CountDownLatch latch) {
        return () -> {
            ph.register(); //동기화 과정에 참여할 수 있는 쓰레드 수를 늘림
            try {
                Thread.sleep(1000L);
                System.out.println(Thread.currentThread().getName() + " arrived");
                ph.arriveAndAwaitAdvance(); //다른 쓰레드가 대기 상태가 되길 기다리는 지점

                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " sleep end");
            ph.arriveAndDeregister(); //위에서 늘린 쓰레드 수를 줄인다
            latch.countDown();
        };
    }

    @DisplayName("Phaser Test")
    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5); //자식 쓰레드 종료를 대기하기 위해 추가
        Phaser phaser = new Phaser(1); //초기 phaser에 참여할 쓰레드 갯수 지정

        //쓰레드 생성
        for (int i = 0; i < 5; i++) {
            new Thread(run(phaser, latch)).start();
        }

        assertEquals(0, phaser.getPhase()); //phase가 시작하고 끝이 안났을 떄

        System.out.println(Thread.currentThread().getName() + " arrived");
        phaser.arriveAndAwaitAdvance(); //arrive되고 대기하는 상태

        System.out.println("main await end");
        latch.await();

        assertEquals(1, phaser.getPhase()); //첫 phase가 시작되고 끝이 남

        System.out.println("==============");

        latch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            new Thread(run(phaser, latch)).start();
        }

        System.out.println(Thread.currentThread().getName() + " arrived");
        phaser.arriveAndAwaitAdvance();
        System.out.println("main await end");

        assertEquals(2, phaser.getPhase());

        latch.await();
    }
}
