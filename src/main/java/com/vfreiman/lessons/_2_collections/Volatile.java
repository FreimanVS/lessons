package com.vfreiman.lessons._2_collections;

import java.util.concurrent.*;

/*
    Reordering. Other threads can see fields in other ordering than the main thread.
 */
public class Volatile {

    boolean first = false;
//    volatile
        boolean second = false;

    void setValues() {
        first = true;
        second = true;
    }

    void checkValues() {
        while(!second) {
//            System.out.println(first + "!"); //if uncomment there is no need in volatile (?)
        }
        System.out.println(first);
    }

    private static final ExecutorService es = Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Volatile().main();
    }

    private void main() throws ExecutionException, InterruptedException {
        Future<?> submit = es.submit(() -> {
            checkValues();
        });

        Future<?> submit1 = es.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setValues();
        });

        submit.get();
        submit1.get();

        es.shutdown();
    }
}
