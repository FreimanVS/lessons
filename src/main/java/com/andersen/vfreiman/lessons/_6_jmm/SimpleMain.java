//package com.andersen.vfreiman.lessons._6_jmm;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.*;
//import java.util.stream.IntStream;
//
//public class SimpleMain {
//    public static volatile int i = 0;
//    public static void main(String[] args) throws InterruptedException {
//
//
//            new Thread(() -> {
//                doSmth();
//            }).start();
//
//        while(true) {
//            doSmth();
//        }
//
//        new ExecutorService() {
//
//            @Override
//            public void shutdown() {
//
//            }
//
//            @Override
//            public List<Runnable> shutdownNow() {
//                return null;
//            }
//
//            @Override
//            public boolean isShutdown() {
//                return false;
//            }
//
//            @Override
//            public boolean isTerminated() {
//                return false;
//            }
//
//            @Override
//            public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
//                return false;
//            }
//
//            @Override
//            public <T> Future<T> submit(Callable<T> task) {
//                return null;
//            }
//
//            @Override
//            public <T> Future<T> submit(Runnable task, T result) {
//                return null;
//            }
//
//            @Override
//            public Future<?> submit(Runnable task) {
//                return null;
//            }
//
//            @Override
//            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
//                return null;
//            }
//
//            @Override
//            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
//                return null;
//            }
//
//            @Override
//            public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
//                return null;
//            }
//
//            @Override
//            public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
//                return null;
//            }
//
//            @Override
//            public void execute(Runnable command) {
//
//            }
//        }
//    }
//
//    private static void doSmth() {
//        while(true) {
//            int s = i;
////            System.out.println(Thread.currentThread().getName() + " said hi");
////            try {
////                TimeUnit.SECONDS.sleep(1);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
//    }
//}
