package com.vfreiman.lessons._6_jmm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Visibility {
    private static volatile int a = 0;
    private static final ReentrantLock lock = new ReentrantLock(false);
    private static final ExecutorService es = Executors.newCachedThreadPool();
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final List<Future<?>> submits = new ArrayList<>();
    private static final int TIMES = 10;
    private void increase() {
        a ++;
        lock.lock();
        System.out.println(a);
        lock.unlock();
    }

    private void main() {
        IntStream.rangeClosed(1, TIMES).forEach((n) -> {
            Future<?> submit = es.submit(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                increase();
            });
            submits.add(submit);
        });
        latch.countDown();
        submits.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        es.shutdown();
    }

    public static void main(String[] args) {
        new Visibility().main();
    }
}
