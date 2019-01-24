package com.vfreiman.lessons._6_jmm;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/*
While a thread is writing to a variable other threads are blocked to read from that variable
Does a write operation have more priority than a read operation?
writing in a volatile variable happens-before reading from that variable
not only in a VOLATILE?
 */
public class Volatile {
    private /*volatile*/ Long i = 0L;
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
                i = new Long(n);
            });
        });

        IntStream.rangeClosed(1, TIMES).forEach((n) -> {
            es.submit(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(i);
            });
        });

        latch.countDown();
        es.shutdown();
    }

    public static void main(String[] args) {
        new Volatile().main();
    }
}
