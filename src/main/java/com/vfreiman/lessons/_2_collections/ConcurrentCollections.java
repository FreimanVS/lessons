package com.vfreiman.lessons._2_collections;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCollections {

    private static final ReentrantLock LOCK = new ReentrantLock(false);
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    private static final int TIMES = 10000000;
    private static final int SIZE = TIMES * 10;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        queue();
//        set();
//        map();
//        list();
    }

    private static void queue() throws ExecutionException, InterruptedException {
        Queue<Integer> q =
//                new PriorityQueue<>();
                new ArrayDeque<Integer>();
//                new ConcurrentLinkedQueue<>(); //slow
//                new ConcurrentLinkedDeque<>(); //slow
//                new ArrayBlockingQueue<>(SIZE);
//                new LinkedBlockingQueue<>(SIZE); //slow
//                new LinkedBlockingDeque<>(); //slow
//                new PriorityBlockingQueue<>();
//                new SynchronousQueue<>(); //one entered one exited
//                new LinkedTransferQueue<>(); //slow

//        Collection<Integer> q = Collections.synchronizedCollection(new ArrayDeque<>());
//        Queue<Delayed> q = new DelayQueue<Delayed>();

        Future<?> key = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                q.add(1);
            }
        });

        Future<?> key1 = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                q.add(1);
            }
        });

        key.get();
        key1.get();
        System.out.println(q.size());

        ES.shutdown();
    }

    private static void list() throws ExecutionException, InterruptedException {

        final List<Integer> list =
            new ArrayList<>();
//            Collections.synchronizedList(new ArrayList<Integer>());
//            new CopyOnWriteArrayList<Integer>();

        Future<?> key = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                list.add(0);
            }
        });

        Future<?> key1 = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                list.add(0);
            }
        });

        key.get();
        key1.get();
        System.out.println(list.size());

        ES.shutdown();
    }

    private static void map() throws ExecutionException, InterruptedException {

        final Map<Integer, Object> map =
//            new HashMap<Integer, Object>();
//            new ConcurrentHashMap<Integer, Object>();
//            new ConcurrentSkipListMap<Integer, Object>();
//            Collections.synchronizedMap(new HashMap<Integer, Object>());
//            Collections.synchronizedMap(new LinkedHashMap<Integer, Object>());
            Collections.synchronizedNavigableMap(new TreeMap<Integer, Object>());

        int[] keys1 = new int[]{0};
        Future<?> key = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                map.put(keys1[0]++, new Object());
            }
        });

        int[] keys2 = new int[]{20000001};
        Future<?> key1 = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                map.put(keys2[0]++, new Object());
            }
        });

        key.get();
        key1.get();
        System.out.println(map.size());

        ES.shutdown();
    }

    private static void set() throws ExecutionException, InterruptedException {
        Set<Integer> set =
                new HashSet<>();
//                new CopyOnWriteArraySet<>(); //slow
//                new ConcurrentSkipListSet<>(); //concurrentTreeSet
//                Collections.synchronizedSet(new HashSet<>());
//                Collections.synchronizedNavigableSet(new TreeSet<>());

        int[] keys1 = new int[]{0};
        Future<?> key = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                set.add(keys1[0]++);
            }
        });

        int[] keys2 = new int[]{20000001};
        Future<?> key1 = ES.submit(() -> {
            for (int i = 0; i < TIMES; i++) {
                set.add(keys2[0]++);
            }
        });

        key.get();
        key1.get();
        System.out.println(set.size());

        ES.shutdown();
    }
}
