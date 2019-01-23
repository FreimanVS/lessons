package com.andersen.vfreiman.lessons._4_multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class Deadlocks {

    public static void main(String[] args) {
        deadlock();
//        deadlock2();
    }

    public static void deadlock2() {
        Deadlock2 business = new Deadlock2();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                business.foo();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                business.bar();
            }
        });

        t1.start();
        t2.start();
    }



    public static void deadlock() {
        final Deadlock.Friend alphonse =
                new Deadlock.Friend("Alphonse");
        final Deadlock.Friend gaston =
                new Deadlock.Friend("Gaston");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // System.out.println("Thread 1");
                alphonse.bow(gaston);
                // System.out.println("Th: gaston bowed to alphonse");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //  System.out.println("Thread 2");
                gaston.bow(alphonse);
                //  System.out.println("2.gaston waiting alph bowed");
            }
        }).start();
    }

    public static class Deadlock2 {

        private Object lock1 = new Object();
        private Object lock2 = new Object();

        public void foo() {
            synchronized (lock1) {
                synchronized (lock2) {
                    System.out.println("foo");
                }
            }
        }

        public void bar() {
            synchronized (lock2) {
                synchronized (lock1) {
                    System.out.println("bar");
                }
            }
        }
    }

    public static class Deadlock {

        static class Friend {
            private final String name;

            private final ReentrantLock lock = new ReentrantLock(false);

            public Friend(String name) {
                this.name = name;
            }
            public String getName() {
                return this.name;
            }
            public void bow(Friend bower) {
                lock.lock();
                try {
                    System.out.format("%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
                    bower.bowBack(this);
                } finally {
                    lock.unlock();
                }
            }
            public void bowBack(Friend bower) {
                lock.lock();
                try {
                    System.out.format("%s: %s"
                                    + " has bowed back to me!%n",
                            this.name, bower.getName());
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
