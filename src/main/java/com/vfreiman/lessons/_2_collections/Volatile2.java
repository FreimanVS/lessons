package com.vfreiman.lessons._2_collections;

import java.util.concurrent.*;


/*
FIRST: 1
FIRST: 1
FIRST: 1
BEFORE SECOND
FIRST: 1
AFTER SECOND
FIRST: 2
 */
public class Volatile2 {

//    volatile
        int first = 1;

    private static final ExecutorService es = Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Volatile2().main();
    }

    private void main() throws ExecutionException, InterruptedException {
        Future<?> submit = es.submit(() -> {
            while (first == 1) {
                System.out.println("FIRST: " + first);
            }
            System.out.println("FIRST: " + first);
        });

        Future<?> submit1 = es.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("BEFORE SECOND");
            first = 2;
            System.out.println("AFTER SECOND");
        });

        submit.get();
        submit1.get();

        es.shutdown();
    }

}
