package com.vfreiman.lessons._6_jmm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Volatile2 {
    private volatile int i = 0;
    private static final int TIMES = 100;
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final ExecutorService es = Executors.newCachedThreadPool();

    private void main() {
        es.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            IntStream.rangeClosed(1, TIMES).forEach((n) -> {
                System.out.println(i);
            });
        });

        IntStream.rangeClosed(1, TIMES).forEach((n) -> {
            es.submit(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i = n;
            });
        });

        latch.countDown();
        es.shutdown();
    }

    public static void main(String[] args) {
        new Volatile2().main();
    }
}
