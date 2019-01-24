package com.vfreiman.lessons._6_jmm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SimulationVolatile {

    private int i;
    private static final ReentrantLock lock = new ReentrantLock(false);
    private static final ExecutorService es = Executors.newCachedThreadPool();
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final int TIMES = 100;

    private void write(int n) {
        lock.lock();
        try {
            this.i = n;
        } finally {
            lock.unlock();
        }
    }

    private int read() {
        lock.lock();
        try {
            return this.i;
        } finally {
            lock.unlock();
        }
    }

    private void main() {
        es.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            IntStream.rangeClosed(1, TIMES).forEach((n) -> {
                write(n);
            });
        });

        IntStream.rangeClosed(1, TIMES).forEach((n) -> {
            es.submit(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(read());
            });
        });

        latch.countDown();
        es.shutdown();
    }

    public static void main(String[] args) {
        new SimulationVolatile().main();
    }
}
