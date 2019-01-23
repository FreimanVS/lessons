package com.vfreiman.lessons._4_multithreading;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Starvation {

    public static void main(String[] args) {

        Worker worker = new Worker();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    worker.work();
                }
            }).start();
        }
    }

    private static class Worker {

        private static ReentrantLock lock = new ReentrantLock(false);
        private static Condition condition = lock.newCondition();

        public void work() {
            lock.lock();
            try {
                String name = Thread.currentThread().getName();
                String fileName = name + ".txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write("Thread " + name + " wrote this mesasge");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                while (true) {
                    System.out.println(name + " is working");
                    TimeUnit.SECONDS.sleep(1);
//                    condition.await(1, TimeUnit.SECONDS); //solution
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
