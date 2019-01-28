package com.vfreiman.lessons._7_patterns.structural;

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
