package com.andersen.vfreiman.lessons._4_multithreading;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    static int b = 10;
    Map<String, String> map = new HashMap<>();

    //only n threads can work at the same time, others wait for them
    Semaphore semaphore = new Semaphore(1, true);

    //Atomacity. LOCKER.lock() -> an atomic task -> LOCKER.unlock()
    //works like synchronized
    ReentrantLock LOCKER = new ReentrantLock(true);
    //works like wait, notify
    Condition CONDIITION = LOCKER.newCondition();

    //all threads wait for any other thread(-s) do latch.countDown() n times
    CountDownLatch latch = new CountDownLatch(1);

    //work with volatile and atomacity
    AtomicInteger atomicInt = new AtomicInteger();

    //the same as ReentrantLock but RL.lock() doesn't block other RL.lock()
    ReentrantReadWriteLock RWLOCKER = new ReentrantReadWriteLock(true);
    ReentrantReadWriteLock.ReadLock RL = RWLOCKER.readLock();
    ReentrantReadWriteLock.WriteLock WL = RWLOCKER.writeLock();

    //n threads wait for all of them to be on the point and then they all work
    CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
        System.out.println("Barrier falled");
    });

    //exchange data between threads
    Exchanger<String> exchanger = new Exchanger<>();

    Phaser phaser = new Phaser(2);

    volatile long i = 0L;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        DiscordService.getToken();
//        String token = "NTIxMzk5NjE0NDc4MjIxMzIz.Du8GxQ.6dYAO_fH3xGgy8PUUaF7BSHLiy0";
//        IDiscordClient client = DiscordService.createClient(token, false);
//        System.out.println(client.getChannels());

//         System.out.println(TwitchService.getUserByLogin("sasliminta1982"));

//          System.out.println(Translater.theMostRus("Привет. Как are?"));

        new Main().main();
    }

    public void main() throws ExecutionException, InterruptedException {
        ExecutorService es =
//                Executors.newFixedThreadPool(100); //in order of adding tasks
//                Executors.newWorkStealingPool(5); //not in order of adding tasks, free thrads steal tasks
                Executors.newCachedThreadPool(
//                        (r) -> {Thread t = new Thread(r); t.setDaemon(true); return t;} //a custom deamon factory
                ); //pool of threads which constantly changes its size depending on loading the program

//        Executors.newFixedThreadPool() //ExecutorService => ThreadPoolExecutor => BlockingArrayQueue
//        Executors.newCachedThreadPool(); //ExecutorService => TThreadPoolExecutor => SynchronousQueue
//        Executors.newScheduledThreadPool() //ExecutorService => TThreadPoolExecutor => DelayedWorkQueue
//        Executors.newWorkStealingPool(); //ExecutorService => TForJoinPool

        System.out.println("Main Thread started");

        /**
         * atomics
         */
        atomics();

        /**
         * synchronizers
         */
//        synchronizers();

        /**
         * locks
         */
//        locks();

        /**
         * pools
         */
//        pools();

        /**
         * CompletableFuture tasks
         */
//        completableFutureTasks();

        /**
         * as if CyclicBarrier
         */
//        int register = phaser.register(); //+1 party to Phaser
//        IntStream.range(0, 10).forEach((n) -> {
//            es.submit(() -> {
////                phaser.arriveAndAwaitAdvance(); //equals to CyclicBarrier.await()
//
////                phaser.arrive(); //go through the barrier without waiting it's completed with N threads
////                int i = phaser.arriveAndDeregister();//arrive and -1 party from Phaser
//                System.out.println("hi from " + Thread.currentThread().getName());
//            });
//        });

        /**
         * as if CountDownLatch
         */
//        IntStream.range(0, 10).forEach((n) -> {
//            es.submit(() -> {
//                phaser.awaitAdvance(phaser.getPhase());
//
//                System.out.println("hi from " + Thread.currentThread().getName());
//            });
//        });
//        phaser.arriveAndDeregister(); //2-1 if new Phaser(2);
//        sleep(2);
//        phaser.arriveAndDeregister(); //2-2=0, threads are released

//        es.submit(() -> {
//            try {
//                String exchange = exchanger.exchange("hi from thread 1");
//                System.out.println(exchange);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        es.submit(() -> {
//            try {
//                String exchange = exchanger.exchange("hi from thread 2");
//                System.out.println(exchange);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

//        IntStream.range(0, 2).forEach((n) -> {
//            es.submit(() -> {
//                try {
//                    LOCKER.lock();
//                    System.out.println("wait " + n);
//                    sleep(3);
//                    CONDIITION.await();
//                    System.out.println("released " + n);
//                    sleep(3);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    LOCKER.unlock();
//                }
//            });
//        });
//
//        sleep(2);
//
//        IntStream.range(0, 1).forEach((n) -> {
//            es.submit(() -> {
//                try {
//                    LOCKER.lock();
//                    System.out.println("notify entered " + n);
//                    sleep(2);
//                    System.out.println("signal " + n);
//                    CONDIITION.signalAll();
//                    sleep(2);
//                    System.out.println("notify finished " + n);
//                } finally {
//                    LOCKER.unlock();
//                }
//            });
//        });

//        IntStream.range(0, 5).forEach((n) -> {
//            es.submit(() -> {
//                try {
//                    System.out.println("wait " + n);
//                    int await = cyclicBarrier.await();
//                    System.out.println("done " + n);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//            });
//        });


//        es.submit(() -> {
//            WL.lock();
//            try {
//                System.out.println("write 1");
//                Utils.sleep(2);
//
//                map.put("a", "a");
//
//                Utils.sleep(2);
//                System.out.println("write 2");
//
//            } finally {
//                WL.unlock();
//            }
////                System.out.println(n + " " + Utils.readFromFile("write1write2.txt"));
////                Utils.writeToFile(n + System.lineSeparator(), "write1write2.txt", true);
////                try {
////                    TimeUnit.SECONDS.sleep(2);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                try {
////                    semaphore.acquire();
////                    System.out.println("thread " + n + " started");
////                    Thread.sleep(10L);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println("thread " + n + " completed");
////                    semaphore.release();
////                }
//        });
//
//
//        new ArrayList<String>() {
//            {
//
//            }
//        };
//
//
//        es.submit(() -> {
//            try {
//                RL.lock();
//                System.out.println("read 11");
//                TimeUnit.SECONDS.sleep(2);
//
//
//                System.out.println(map.get("a"));
//
//
//                System.out.println("read 12");
//                TimeUnit.SECONDS.sleep(2);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                RL.unlock();
//            }
//        });
//
//        Future<?> f = es.submit(() -> {
//            try {
//                RL.lock();
//                System.out.println("read 21");
//                TimeUnit.SECONDS.sleep(2);
//
//
//                System.out.println(map.get("a"));
//
//
//                System.out.println("read 22");
//                TimeUnit.SECONDS.sleep(2);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                RL.unlock();
//            }
//        });
//
//
//        es.submit(() -> {
//            WL.lock();
//            try {
//                System.out.println("write 3");
//                Utils.sleep(2);
//
//                map.put("b", "b");
//
//                Utils.sleep(2);
//                System.out.println("write 4");
//
//            } finally {
//                WL.unlock();
//            }
//        });

        closeExecutorService(es);
        System.out.println("Main Thread ended");
    }

    private void atomics() {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        System.out.println(atomicInteger.incrementAndGet());

        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        System.out.println(atomicBoolean.getAndSet(false));
        System.out.println(atomicBoolean.get());

        AtomicLong atomicLong = new AtomicLong(10L);
        System.out.println(atomicLong.incrementAndGet());

        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[] {2, 3, 1});
        System.out.println(atomicIntegerArray.incrementAndGet(2));

        AtomicLongArray atomicLongArray = new AtomicLongArray(new long[] {2, 3, 1});
        System.out.println(atomicLongArray.accumulateAndGet(1, 5L, (l1, l2) -> l1 + l2));

        AtomicReference<Object> atomicReference = new AtomicReference<>(new Object());
        System.out.println(atomicReference.updateAndGet((n) -> n.hashCode()));

        Object obj = new Object();
        AtomicMarkableReference<Object> atomicMarkableReference = new AtomicMarkableReference<>(obj, true);
        System.out.println(atomicMarkableReference.isMarked());
        atomicMarkableReference.attemptMark(obj, false);
        System.out.println(atomicMarkableReference.isMarked());

        AtomicStampedReference<Object> atomicStampedReference = new AtomicStampedReference<>(obj, 1);
        System.out.println(atomicStampedReference.getStamp());
        atomicStampedReference.attemptStamp(obj, 5);
        System.out.println(atomicStampedReference.getStamp());

        AtomicReferenceArray<Object> objectAtomicReferenceArray = new AtomicReferenceArray<>(new Object[]{obj, "hi"});
        System.out.println(objectAtomicReferenceArray.get(1));
        objectAtomicReferenceArray.compareAndSet(1, "hi", "hi2");//the Object is from the string pool
        System.out.println(objectAtomicReferenceArray.get(1));

        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = new AtomicIntegerFieldUpdater() {
            @Override
            public boolean compareAndSet(Object obj, int expect, int update) {
                return false;
            }

            @Override
            public boolean weakCompareAndSet(Object obj, int expect, int update) {
                return false;
            }

            @Override
            public void set(Object obj, int newValue) {

            }

            @Override
            public void lazySet(Object obj, int newValue) {

            }

            @Override
            public int get(Object obj) {
                return 0;
            }
        };
        //AtomicLongFieldUpdater,AtomicReferenceFieldUpdater
    }

    private void synchronizers() {
        MyOwnableSynchronizer.method();
        MyQueuedSynchronizer.method();
        MyQueuedLongSynchronizer.method();
    }
    private static class MyOwnableSynchronizer extends AbstractOwnableSynchronizer {
        public static void method() {
            MyOwnableSynchronizer mySynchronizer = new MyOwnableSynchronizer();

            Thread thread = new Thread(() -> {
                while(true) {
                    System.out.println("hi");
                }
            });
            mySynchronizer.setExclusiveOwnerThread(thread);

            Thread exclusiveOwnerThread = mySynchronizer.getExclusiveOwnerThread();
        }
    }
    private static class MyQueuedSynchronizer extends AbstractQueuedSynchronizer {
        public static void method() {
            MyQueuedSynchronizer mySynchronizer = new MyQueuedSynchronizer();

            Thread thread = new Thread(() -> {
                while(true) {
                    System.out.println("hi");
                }
            });
            mySynchronizer.setExclusiveOwnerThread(thread);
            mySynchronizer.setState(1);

            Thread exclusiveOwnerThread = mySynchronizer.getExclusiveOwnerThread();
            //... there are a lot of methods
        }
    }
    private static class MyQueuedLongSynchronizer extends AbstractQueuedLongSynchronizer {
        public static void method() {
            MyQueuedLongSynchronizer mySynchronizer = new MyQueuedLongSynchronizer();

            Thread thread = new Thread(() -> {
                while(true) {
                    System.out.println("hi");
                }
            });
            mySynchronizer.setExclusiveOwnerThread(thread);
            mySynchronizer.setState(1);

            Thread exclusiveOwnerThread = mySynchronizer.getExclusiveOwnerThread();
            //... there are a lot of methods
        }
    }

    private void locks() {
        Thread thread = new Thread(() -> {
            LockSupport.park(); //suspend the thread
            while(true) {
                System.out.println("hi");
                sleep(1);
            }
        });
        thread.start();
        sleep(2);
//        Object blocker = LockSupport.getBlocker(thread);
//        LockSupport.park();
        LockSupport.unpark(thread); //resume the thread
//        System.out.println(blocker);
    }

    public static void processHolder() {
        Executors.newSingleThreadExecutor().submit(() -> LockSupport.park());
//        new Thread(() -> LockSupport.park()).start();
    }

    private void pools() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executorService);
        Future<String> submit = ecs.submit(() -> "hi");
        Future<String> take = ecs.take();
//
//        String s = submit.get();
//        String s1 = take.get();
//        System.out.println(s);
//        System.out.println(s1);

//        ForkJoinTask<String> adaptTask = RecursiveTask.adapt(() -> "hi");
//        ForkJoinTask<?> adaptAction = RecursiveAction.adapt(() -> System.out.println("hi"));
//
//        ForkJoinTask<String> forkJoinTask = ForkJoinTask.adapt(() -> "hi"); //forkJoinTask from Runnable or Callable
////        ForkJoinTask<String> forkJoinTask = new ForkJoinTask<String>() {
////            @Override
////            public String getRawResult() {
////                return null;
////            }
////
////            @Override
////            protected void setRawResult(String value) {
////
////            }
////
////            @Override
////            protected boolean exec() {
////                return false;
////            }
////        };
//
//        ForkJoinTask<String> forkJoinTask2 = forkJoinTask.fork();
//        String join = forkJoinTask.join();
//        String join1 = forkJoinTask2.join();
//        String invoke = forkJoinTask.invoke();
//        String invoke1 = forkJoinTask2.invoke();

//        new ThreadPoolExecutor();
//        new ScheduledThreadPoolExecutor();
//        new ForkJoinPool();

//        FutureTask<String> stringFutureTask = new FutureTask<>(() -> "hi");
//        es.submit(stringFutureTask);
//        System.out.println("Future task's result : " + stringFutureTask.get());
    }

    private void completableFutureTasks() {

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hi", ses);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Hi2");
//        CompletableFuture<Void> hi = CompletableFuture.runAsync(() -> {
//            while (true) {
//                System.out.println("hi");
//                sleep(1);
//            }
//        }, Executors.newCachedThreadPool());
//        System.out.println("continues");
//
//
//        Executors.newSingleThreadExecutor().submit(() -> {
//            try {
//                new Semaphore(0).acquire();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });




//        CompletableFuture<String> result = CompletableFuture
//                .runAsync(() -> {
//                    System.out.println("hi before");
//
//                    sleep(2);
//
//                    System.out.println("hi after");
//                })
//                .thenApply((v0id) -> {
//
//                    System.out.println("hi2 before");
//
//                    sleep(2);
//
//                    System.out.println("hi2 after");
//
//                    return "hi";
//                })
//                .handle((obj, err) -> {
//                    System.out.println("OBJ: " + obj);
//                    if (Objects.nonNull(err)) System.err.println(err.getMessage());
//                    return obj;
//                });
////        System.out.println(result.join());
//        sleep(5);
//        System.out.println(result.isDone());
//        System.out.println("THE RESULT IS " + result.join()); //join is get without an exception




//        boolean flag = true;
//        CompletableFuture
//                .supplyAsync(() -> {
//                    if (flag) {
//                        throw new RuntimeException("error in async running");
//                    } else
//                        return "no error";
//                })
//                .exceptionally(err -> err.getMessage())
//                .thenAccept(System.out::println);

        String collect = Stream.of(future, future2).map(f -> f.join()).collect(Collectors.joining(", ", "[", "]"));
        closeExecutorService(ses);
        System.out.println(collect);
    }

    public static void closeExecutorService(ExecutorService es) {
        try {
            es.shutdown();
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(es + " termination interrupted");
        } finally {
            if (!es.isTerminated()) {
                System.err.println(es + " killing non-finished tasks");
                es.shutdownNow();
            } else {
                System.out.println(es + " terminated");
            }

        }
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
