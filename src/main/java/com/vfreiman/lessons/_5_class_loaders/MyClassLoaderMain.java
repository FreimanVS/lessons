package com.vfreiman.lessons._5_class_loaders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyClassLoaderMain {

    public static void main(String[] args) {

        final ExecutorService es = Executors.newFixedThreadPool(2);

        final String prefix = "myclassloaderpath";
        final String classToLoad =
                SomeClass.class.getName();
        final ClassLoader myClassLoader = new MyClassLoader(prefix);
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        Future<?> myClassLoaderThread = es.submit(() -> {
            Thread.currentThread().setName("myClassLoaderThread");
            Thread.currentThread().setContextClassLoader(myClassLoader);
            invokeSomeMethod(classToLoad);
        });

        Future<?> systemClassLoaderThread = es.submit(() -> {
            Thread.currentThread().setName("systemClassLoaderThread");
            Thread.currentThread().setContextClassLoader(systemClassLoader);
            invokeSomeMethod(classToLoad);
        });

        es.shutdown();
    }

    private static void invokeSomeMethod(String classToLoad) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("Current class loader: " + contextClassLoader);

        try {
            final Class<?> cl = contextClassLoader.loadClass(classToLoad);

            final Class<?> cl2 = Class.forName(
                    classToLoad,
                    true,
                    contextClassLoader);

            invokeSomeMethod(cl);
            invokeSomeMethod(cl2);
        } catch (final ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void invokeSomeMethod(final Class<?> cl) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        final Object instance = cl.newInstance();
        final Method someMethod = cl.getMethod("someMethod");
        someMethod.invoke(instance);
    }
}
