package com.andersen.vfreiman.lessons._4_multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class WebServer {

//    private static final Executor exec = new MyExecutor();
//    private static final Executor exec = Executors.newCachedThreadPool();
    private static final ExecutorService exec = Executors.newFixedThreadPool(10);

    private static final Executor myExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    };

    private static final ExecutorService myFixedThreadPoolExecutor =
            new ThreadPoolExecutor(2, 2, 0, TimeUnit.NANOSECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            return new Thread(r);
                        }
                    },
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            throw new RuntimeException("ThreadPool is full or ES is shutdowned");
                        }
                    })

            {

                //how to execute submit()
                @Override
                protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
                    return super.newTaskFor(callable);
                }

                //initially core threads are lazy.
                //call it to make them work from start
                @Override
                public boolean prestartCoreThread() {
                    return super.prestartCoreThread();
                }

                //initially core threads don't have timeout
                //call it if you want them to have timeout
                @Override
                public boolean allowsCoreThreadTimeOut() {
                    return super.allowsCoreThreadTimeOut();
                }
            };

    private static final Future<String> myFuture = new Future<String>() {
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    private static final Future<String> myRunnableFuture = new RunnableFuture<String>() {
        @Override
        public void run() {

        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    private static final FutureTask<String> myFutureTask = new FutureTask<String>(() -> "hi");

    public static void main(String[] args) {

//        try {
//            ServerSocket serverSocket = new ServerSocket(80);
//
//            while (true) {
//                Socket connection = serverSocket.accept();
//                myFixedThreadPoolExecutor.execute(() -> {
//                    handle(connection);
//                });
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            myFixedThreadPoolExecutor.shutdown();
//        }




        myExecutor.execute(() -> {
            System.out.println("hi");
        });
        myExecutor.execute(() -> {
            System.out.println("hi3");
        });
        System.out.println("hi2");



        // Future + CompletionStage => CompletableFuture
        //Runnable + Future = RunnableFuture -> FutureTask
        Future<?> submit = myFixedThreadPoolExecutor.submit(() -> "hi"); //equals below
        RunnableFuture<?> runnableFuture = new FutureTask<>(() -> "hi");
        myFixedThreadPoolExecutor.execute(runnableFuture);
        Future<?> f = runnableFuture;
    }

    private static void handle(Socket connection) {
        System.out.println(Thread.currentThread() + " is doing something");
        System.out.println("connection.isConnected() " + connection.isConnected());;
        while (true) {
            System.out.println("true");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        try {
//            connection.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static class MyExecutor implements Executor {
        @Override
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    private static class MyExecutorInCurrentThread implements Executor {
        @Override
        public void execute(Runnable r) {
            r.run();
        }
    }

    private static class MyExecutorService implements ExecutorService {
        @Override
        public void shutdown() {

        }

        @Override
        public List<Runnable> shutdownNow() {
            return null;
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return null;
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return null;
        }

        @Override
        public Future<?> submit(Runnable task) {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }

        @Override
        public void execute(Runnable command) {

        }
    }
}
