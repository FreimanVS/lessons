package com.vfreiman.lessons._4_multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class AtomicReferenceExample {

    private static final A a = new A();
    private static final AtomicReference<A> atomicReference = new AtomicReference<>(a);

    private static final int TIMES = 10000;
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String[] args) {
        final List<CompletableFuture<Void>> cfs = new ArrayList<>();
        final ExecutorService es = Executors.newCachedThreadPool();

        IntStream.rangeClosed(1, TIMES).forEach((n) -> {
            CompletableFuture<Void> cf = CompletableFuture.runAsync(AtomicReferenceExample::operate, es);
            cfs.add(cf);
        });

        LATCH.countDown();

        cfs.forEach(CompletableFuture::join);

        System.out.println(atomicReference.get().getI());

        es.shutdown();
    }

    private static void operate() {
        try {
            LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        atomicReference.getAndUpdate((old) -> {
            A a = new A();
            a.setI(old.getI() + 1);
            return a;
        });
    }

    private static class A {
        private int i;

        private A () {
            i = 0;
        }

        private int getI() {
            return this.i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }
}
