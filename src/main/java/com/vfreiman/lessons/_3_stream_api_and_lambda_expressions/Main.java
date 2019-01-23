package com.vfreiman.lessons._3_stream_api_and_lambda_expressions;

import com.sun.deploy.util.StringUtils;
import com.vfreiman.lessons._2_collections.MyLinkedQueue;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) {
        new Main().main();
    }
    public void main() {
//        functionalInterfaces();
//        collectMethods();
//        reduceMethods();

//        spliterator();
//        spliterator2();
//        spliterator3();
//        spliterator4();
        spliterator5();

        /*
        spliterators:
            trySplit() beret sebe polovinu elements, ostavlyaet druguu polovinu predidushemu spliteratoru
            tryAdvance() beret sebe odin element, esli on est' ili null.
            forEachRemaining vipolnyaet deistviya dlya vseh elementov v etom spliteratore
         */
    }

    private void spliterator5() {
        Stream<String> stream = Stream.of("s1", "s2", "s3", "s4", "s5");
        Spliterator<String> spliterator = stream.spliterator();

        ExecutorService es2 = null;
        try {
            final int TIMES = (int)spliterator.getExactSizeIfKnown();
            final ExecutorService es = Executors.newCachedThreadPool();
            final ReentrantLock lock = new ReentrantLock(false);
            es2 = es;

            IntStream.range(0, TIMES). forEach((n) -> {
                es.submit(() -> {
                    try {
                        lock.lock();
                        spliterator.tryAdvance(System.out::println);
                    } finally {
                        lock.unlock();
                    }
                });
            });
        } finally {
            if (es2 != null)
                es2.shutdown();
        }
    }

    private void spliterator4() {
        final Spliterator.OfInt spliterator = IntStream.rangeClosed(1, 5).spliterator();

        ExecutorService es2 = null;
        try {
            final int TIMES = (int)spliterator.getExactSizeIfKnown();
            final ExecutorService es = Executors.newCachedThreadPool();
            final ReentrantLock lock = new ReentrantLock(false);
            es2 = es;

            IntStream.range(0, TIMES). forEach((n) -> {
                es.submit(() -> {
                    try {
                        lock.lock();
                        spliterator.tryAdvance((Consumer<? super Integer>) System.out::println);
                    } finally {
                        lock.unlock();
                    }
                });
            });
        } finally {
            if (es2 != null)
                es2.shutdown();
        }
    }

    private void spliterator3() {
        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        //razdelyat list na dve chasti vruchnuu do teh por poka ne budet po 1 elementu v kajdoi chasti
        Spliterator<Integer> spliterator = list.spliterator();

        // Traversing elements in few threads (parallel) splitting them automatically
        ExecutorService es2 = null;
        try {
            final int TIMES = list.size();
            final ExecutorService es = Executors.newCachedThreadPool();
            es2 = es;

            IntStream.range(0, TIMES). forEach((n) -> {
                es.submit(() -> {
                    spliterator.tryAdvance(System.out::println); //tryAdvance = if (hasNext()) consumer(next)
                });
            });
        } finally {
            if (es2 != null)
                es2.shutdown();
        }
    }

    private void spliterator2() {
        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        //razdelyat list na dve chasti vruchnuu do teh por poka ne budet po 1 elementu v kajdoi chasti
        Spliterator<Integer> spliterator1 = list.spliterator();     //split list into two parts
        Spliterator<Integer> spliterator2= spliterator1.trySplit(); //split spliterator1 into two parts
        Spliterator<Integer> spliterator3= spliterator2.trySplit(); //split spliterator2 into two parts
        Spliterator<Integer> spliterator4= spliterator1.trySplit(); //split spliterator1 into two parts
        Spliterator<Integer> spliterator5= spliterator1.trySplit(); //split spliterator1 into two parts

        //iterate each part consistently each one in a separate thread
        ExecutorService es2 = null;
        try {
            final ExecutorService es = Executors.newCachedThreadPool();
            es2 = es;

            es.submit(() -> {
                spliterator1.forEachRemaining(System.out::println);
            });

            es.submit(() -> {
                spliterator2.forEachRemaining(System.out::println);
            });

            es.submit(() -> {
                spliterator3.forEachRemaining(System.out::println);
            });

            es.submit(() -> {
                spliterator4.forEachRemaining(System.out::println);
            });

            es.submit(() -> {
                spliterator5.forEachRemaining(System.out::println);
            });

        } finally {
            if (es2 != null)
                es2.shutdown();
        }
    }

    private void spliterator() {
        List<String> names = new ArrayList<>();
        names.add("Rams");
        names.add("Posa");
        names.add("Chinni");

        // Getting Spliterator
        Spliterator<String> namesSpliterator = names.spliterator();

        // Traversing elements in one thread
//        namesSpliterator.forEachRemaining(System.out::println);

        Spliterator<String> namesSpliterator2 = namesSpliterator.trySplit(); //split nameSpliterator. Left him the first
                                                      //part of the elements to iterate and iterate the second part self
        namesSpliterator.forEachRemaining(System.out::println);
        namesSpliterator2.forEachRemaining(System.out::println);

        // Traversing elements in few threads (parallel)
//        ExecutorService es2 = null;
//        try {
//            final int TIMES = names.size();
//            final ExecutorService es = Executors.newCachedThreadPool();
//            es2 = es;
//
//            IntStream.range(0, TIMES). forEach((n) -> {
//                es.submit(() -> {
//                    namesSpliterator.tryAdvance(System.out::println);
//                });
//            });
//        } finally {
//            if (es2 != null)
//                es2.shutdown();
//        }
    }

    private void primitiveStreams() {
        IntStream intStream = IntStream.of(1, 2, 3, 4);
        LongStream longStream = LongStream.of(5, 6, 7, 8);
        DoubleStream doubleStream = DoubleStream.of(9, 10, 11, 12);

        IntStream intStream2 = IntStream.range(1, 100); // от 1 до 99
        LongStream longStream2 = LongStream.range(2, 200); // от 1 до 99

        IntStream intStream3 = IntStream.rangeClosed(1, 100); // от 1 до 100
        LongStream longStream3 = LongStream.rangeClosed(2, 200); // от 1 до 100

        DoubleStream doubleStream4 = IntStream.rangeClosed(1, 100)
                .asLongStream()
                .asDoubleStream();

        int[] ints = IntStream.of(1, 2).toArray(); // [1, 2]
        long[] longs = LongStream.of(3, 4).toArray(); // [3, 4]
        double[] doubles = DoubleStream.of(5, 6).toArray(); // [5.0, 6.0]

        int intSum = IntStream.of(1, 2).sum(); // 3
        long longSum = LongStream.of(3, 4).sum(); // 7
        double doubleSum = DoubleStream.of(5, 6).sum(); // 11

        OptionalDouble intAverage = IntStream.of(1, 2).average(); // 1.5
        OptionalDouble longAverage = LongStream.of(3, 4).average(); // 3.5
        OptionalDouble doubleAverage = DoubleStream.of(5, 6).average(); // 5.5
    }

    private void reduceMethods() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        Integer identity = 10;
        BiFunction<Integer, Integer, Integer> accumulator = (ident, t) -> ident * t;
        BinaryOperator<Integer> combiner = (t1, t2) -> t1 + t2;

                // 1*10 + 2*10 + 3*10
        Integer sum = numbers.stream().reduce(identity, accumulator, combiner);

        System.out.println(sum); //output 60
    }

    private void functionalInterfaces() {

        UserFactory userFactory = new UserFactory() {
            @Override
            public User create(String name, String surname) {
                return new User(name, surname);
            }
        };

        User user1 = userFactory.create("hi", "hi2");
        User user2 = userFactory.create("hi3", "hi4");

        UserFactory userFactory3 = User::new;
        User user3 = userFactory3.create("John", "Snow");

        Function<String, String> trim = String::trim;
        Function<String, StringBuilder> stringStringBuilderFunction = trim
                .andThen(String::toLowerCase)
                .andThen(StringBuilder::new);

        Comparator<User> comparing = Comparator.comparing(User::getName);

        String[] strings = StringUtils.splitString("asdf,asfd2,asdf3", ",");

        MyFunctionalInterace<String> myFunctionalInterace =
                ((arg1, arg2) -> new MyObject(arg1 + ", hi2!", arg2));

        MyFunctionalInterace<String> myFunctionalInterace2 = MyObject::new;

        myFunctionalInterace2.myMethod("12345", "54321");
    }

    private static void collectMethods() {
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5", "5", "5");

        Supplier<ArrayList<Integer>> supplier = () -> new ArrayList<Integer>();
        BiConsumer<ArrayList<Integer>, Integer> accumulator = (list, number) -> list.add(number);
        BiConsumer<ArrayList<Integer>, ArrayList<Integer>> biConsumer2 = (list1, list2) -> list1.addAll(list2);

//        Collection<Integer> evenNumbers =
//        Map<Integer, Integer> evenNumbers =
//        HashMap<String, String> collection =
//        String collection =
//        IntSummaryStatistics collection =
//        Map<Boolean, String> collection =
        Map<Boolean, HashMap<String, String>> collection = numbers.stream()
//                .filter(i -> i % 2 == 0)
                .collect(
//                        supplier,
//                        accumulator,
//                        biConsumer2

//                        collector1()

//                        collectorOfCollector()
//                        toMapCollector()
//                        groupingByCollector()
//                        collectingAndThenCollector()
//                        mappingCollector()
//                        reducingCollector()
//                        summarizingIntCollector()
                        partitioningByCollector()
                );
        System.out.println(collection);
    }

    private static Collector<String, ?, Map<Boolean, HashMap<String, String>>> partitioningByCollector() {
//        Collector<String, ?, Map<Boolean, List<String>>> collector =
//                Collectors.partitioningBy((String t) -> Integer.valueOf(t) > 3);

        Predicate<String> predicate = (t) -> Integer.valueOf(t) > 3;
//        Collector<String, ?, String> stringStringCollector = reducingCollector();
        Collector<String, ?, HashMap<String, String>> stringHashMapCollector = toMapCollector();

//        Collector<String, ?, Map<Boolean, String>> collector =
        Collector<String, ?, Map<Boolean, HashMap<String, String>>> collector =
                Collectors.partitioningBy(predicate
//                        , stringStringCollector
                , stringHashMapCollector
        );

        return collector;
    }

    private static Collector<String, ?, IntSummaryStatistics> summarizingIntCollector() {
        ToIntFunction<String> mapper = s -> s.length();
        Collector<String, ?, IntSummaryStatistics> collector = Collectors.summarizingInt(mapper);
        return collector;
    }

    private static Collector<String, ?, String> reducingCollector() {
        String identity = "initial";
        Function<String, String> mapper = s -> s.concat("#mapped");
        BinaryOperator<String> op = (u1, u2) -> u1.concat(" ").concat(u2);

        Collector<String, ?, String> collector = Collectors.reducing(identity, mapper, op);

        return collector;
    }

    private static Collector<String, ?, HashMap<String, String>> mappingCollector() {
        Function<String, String> mapper = t -> t.concat("+mapper ");
        Collector<String, ?, HashMap<String, String>> downstream = groupingByCollector();
        Collector<String, ?, HashMap<String, String>> collector = Collectors.mapping(mapper, downstream);
        return collector;
    }

    private static Collector<String, StringBuilder, String> collectingAndThenCollector() {
        Collector<String, StringBuilder, String> downstream = myReducingCollector();
        Function<String, String> finisher = s -> s.concat(" + FINISHER");
        Collector<String, StringBuilder, String> collector = Collectors.collectingAndThen(downstream, finisher);

        return collector;
    }

    private static Collector<String, ?, HashMap<String, String>> groupingByCollector() {
        Function<String, String> classifier = t -> t;
        Supplier<HashMap<String, String>> mapFactory = () -> new HashMap<String, String>();
        Collector<String, StringBuilder, String> downstream = myReducingCollector();

        Collector<String, ?, HashMap<String, String>> collector =
                Collectors.groupingBy(classifier, mapFactory, downstream);

        return collector;
    }

    private static Collector<String, StringBuilder, String> myReducingCollector() {

        Supplier<StringBuilder> supplier = () -> new StringBuilder();

        BiConsumer<StringBuilder, String> accumulator = (sb, s) -> sb.append(s);

        BinaryOperator<StringBuilder> combiner = (sb1, sb2) -> {
            sb1.append(sb2);
            return sb1;
        };

        Function<StringBuilder, String> finisher = sb -> sb.toString();

        Collector.Characteristics identityFinish = Collector.Characteristics.IDENTITY_FINISH;
        Collector.Characteristics unordered = Collector.Characteristics.UNORDERED;
        Collector.Characteristics concurrent = Collector.Characteristics.CONCURRENT;

        Collector<String, StringBuilder, String> collector = Collector.of(
                supplier, accumulator, combiner, finisher
//                        ,identityFinish, unordered, concurrent
        );

        return collector;
    }

    private static Collector<String, ?, HashMap<String, String>> toMapCollector() {
        Function<String, String> key = t -> t;
        Function<String, String> value = t -> t;
        BinaryOperator<String> mergeFunction = (value1, value2) -> value1.concat(value2);
        Supplier<HashMap<String, String>> mapSupplier = () -> new HashMap<String, String>();

        Collector<String, ?, HashMap<String, String>> collector = Collectors.toMap(key, value, mergeFunction, mapSupplier);

        return collector;
    }

    private static Collector<Integer, ArrayList<Integer>, List<Integer>> collectorOfCollector() {
        Supplier<ArrayList<Integer>> supplier = () -> new ArrayList<Integer>();

        BiConsumer<ArrayList<Integer>, Integer> accumulator = (list, number) -> list.add(number);

        BinaryOperator<ArrayList<Integer>> combiner = (arrayList1, arrayList2) -> {
            arrayList1.addAll(arrayList2);
            return arrayList1;
        };

        Function<ArrayList<Integer>, List<Integer>> finisher = arrayList -> (List<Integer>)arrayList;

        Collector.Characteristics identityFinish = Collector.Characteristics.IDENTITY_FINISH;
        Collector.Characteristics unordered = Collector.Characteristics.UNORDERED;
        Collector.Characteristics concurrent = Collector.Characteristics.CONCURRENT;

        Collector<Integer, ArrayList<Integer>, List<Integer>> collector =
                Collector.of(
                        supplier, accumulator, combiner, finisher
//                        ,identityFinish, unordered, concurrent
                );

        return collector;
    }

    private static Collector<Integer, ?, MyLinkedQueue<Integer>> collector1() {
        Collector<Integer, ?, MyLinkedQueue<Integer>> collector =
                Collectors.toCollection(() -> new MyLinkedQueue<Integer>());

        return collector;
    }

    class User {
        private String name, surname;

        public User(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    '}';
        }
    }

    interface UserFactory {
        User create(String name, String surname);
    }

    class MyFunctionalImpl implements MyFunctionalInterace<String> {
        @Override
        public MyObject myMethod(String arg1, String arg2) {
            return new MyObject(arg1 + ", hi!", arg2);
        }
    }

    class MyObject {
        MyObject(String s, String s2) {
            System.out.println(s + " has been created");
        }
    }

    @FunctionalInterface
    private static abstract interface MyFunctionalInterace<T> {
        public abstract MyObject myMethod(T arg1, T arg2);
    }
}
