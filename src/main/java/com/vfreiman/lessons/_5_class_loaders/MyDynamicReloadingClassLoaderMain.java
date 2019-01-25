package com.vfreiman.lessons._5_class_loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
    open cmd in the path "src/main/java"
    compile with the command: javac -encoding utf8 com/vfreiman/lessons/_5_class_loaders/MyDynamicReloadingClassLoaderMain.java
    launch with the command: java com.vfreiman.lessons._5_class_loaders.MyDynamicReloadingClassLoaderMain
 */
public class MyDynamicReloadingClassLoaderMain {

    private void main() {

        while (true) {
            try {
                final ClassLoader myDynamicReloadingClassLoader = new MyDynamicReloadingClassLoader();
                final String classToLoad = SomeClass.class.getName();
                final Class clazz= Class.forName(classToLoad,true, myDynamicReloadingClassLoader);
                invokeSomeMethod(clazz);

                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void invokeSomeMethod(final Class<?> cl) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        final Object instance = cl.newInstance();
        final Method someMethod = cl.getMethod("someMethod");
        someMethod.invoke(instance);
    }

    public static void main(String[] args) {
        new MyDynamicReloadingClassLoaderMain().main();
    }
}
