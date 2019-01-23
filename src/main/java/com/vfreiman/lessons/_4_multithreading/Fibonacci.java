package com.vfreiman.lessons._4_multithreading;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {

    private final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();

        Fibonacci f2 = new Fibonacci(n - 2);
        f2.fork();

        return f2.join() + f1.join();
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = forkJoinPool.invoke(new Fibonacci(10));
        System.out.println(result);
        forkJoinPool.shutdown();
    }
}
