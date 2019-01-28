package com.vfreiman.lessons._7_patterns.creational;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Одиночка — это порождающий паттерн проектирования, который гарантирует, что у класса есть только один экземпляр,
 * и предоставляет к нему глобальную точку доступа.
 */
public class Singleton {
    private static volatile Singleton obj;

    private Singleton() {}

    public static Singleton getInstance() {
        if (obj == null) {
            synchronized (Singleton.class) {
                if (obj == null) {
                    obj = new Singleton();
                }
            }
        }
        return obj;
    }

    public static void main(String[] args) {
        final ExecutorService es = Executors.newCachedThreadPool();
        final int TIMES = 10;

        IntStream.range(0, TIMES).forEach((n) -> {
            es.submit(() -> {
                System.out.println(Singleton.getInstance());
            });
        });

        es.shutdown();
    }
}
