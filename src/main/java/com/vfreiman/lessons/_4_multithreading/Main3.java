package com.vfreiman.lessons._4_multithreading;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main3 {
    public static void main(String[] args) {
//        first();
//        threadExceptions();
        yieldTest();
    }

    private static void first() {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("hi");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) {
            {
                setDaemon(true);
            }
        };
        thread.start();
        System.out.println("done");

        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        System.out.println(defaultUncaughtExceptionHandler);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {

            }
        });

        System.out.println(Thread.getDefaultUncaughtExceptionHandler());
    }


    //yeild says JVM that other threads can take its place
    static Date importantEndTime = new Date();
    static Date unImportantEndTime = new Date();
    private static void yieldTest() {

        System.out.println("Create thread 1");

        Thread importantThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    System.out.println("\n Important work " + i);

                    // Notifying the operating system,
                    // this thread gives priority to other threads.
                    Thread.yield();
                }

                // The end time of this thread.
                importantEndTime = new Date();
                printTime();
            }
        };

        // Set the highest priority for this thread.
        importantThread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("Create thread 2");

        Thread unImportantThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    System.out.println("\n  -- UnImportant work " + i);
                }
                // The end time of this thread.
                unImportantEndTime = new Date();
                printTime();
            }
        };

        // Set the lowest priority for this thread.
        unImportantThread.setPriority(Thread.MIN_PRIORITY);

        // Start threads.
        unImportantThread.start();
        importantThread.start();
    }
    private static void printTime() {
        // Interval (Milliseconds)
        long interval = unImportantEndTime.getTime() - importantEndTime.getTime();

        System.out.println("UnImportant Thread - Important Thread = " //
                + interval + " milliseconds");
    }

    private static void threadExceptions() {
        System.out.println("==> Main thread running...");

        Thread thread = new Thread(() -> {
            System.out.println("Thread running ..");

            while (true) {
                Random r = new Random();

                // A random number from 0-99
                int i = r.nextInt(100);
                System.out.println("Next value " + i);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }

                if (i > 70) {
                    // Simulate an exception was not handled in the thread.
                    throw new RuntimeException("Have a problem...");
                }
            }
        });
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("#Thread: " + t);
                System.out.println("#Thread exception message: " + e.getMessage());
            }
        });

        thread.start();
        System.out.println("==> Main thread end...");
    }
}
