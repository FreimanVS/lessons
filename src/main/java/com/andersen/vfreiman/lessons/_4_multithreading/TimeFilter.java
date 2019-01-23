package com.andersen.vfreiman.lessons._4_multithreading;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeFilter {

    public static void main(String[] args) {
        int TIMES = 10;
        ExecutorService es = Executors.newFixedThreadPool(TIMES);
        for (int i = 0; i < TIMES; i++) {
            es.execute(new Work());
        }
        es.shutdown();
    }

    public static class Work implements Runnable {

//        private ThreadLocal<Long> start = new ThreadLocal<>();
        private long start;

        @Override
        public void run() {
//            start.set(System.currentTimeMillis());
            start=System.currentTimeMillis();

            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
//                System.out.println(System.currentTimeMillis() - start.get());
                System.out.println(System.currentTimeMillis() - start);
            }
        }
    }
}
