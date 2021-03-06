package com.vfreiman.lessons._7_patterns.structural;

/**
 * Фасад — это структурный паттерн проектирования, который предоставляет
 * простой интерфейс к сложной системе классов, библиотеке или фреймворку.
 *
 * Фасад полезен, если вы используете какую-то сложную библиотеку со множеством подвижных частей,
 * но вам нужна только часть её возможностей.
 *
 * Когда вы звоните в магазин и делаете заказ по телефону,
 * сотрудник службы поддержки является вашим фасадом ко всем службам и отделам магазина.
 */
public class Facade {

    private A a = new A();
    private B b = new B();

    private void facadeMethod() {
        a.methodA();
        b.methodB();
    }

    private class A {
        void methodA() {
            System.out.println("A");
        }
    }

    private class B {
        void methodB() {
            System.out.println("B");
        }
    }

    private void main() {
        facadeMethod();
    }
    public static void main(String[] args) {
        new Facade().main();
    }
}
