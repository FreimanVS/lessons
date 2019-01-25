package com.vfreiman.lessons._5_class_loaders;

public class SomeClass {
    public void someMethod() {
        System.out.println("someMethod() from SomeClass. SystemClassLoader." +
                " Thread is " + Thread.currentThread().getName());
    }
}
