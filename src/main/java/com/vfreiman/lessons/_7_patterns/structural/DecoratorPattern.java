package com.vfreiman.lessons._7_patterns.structural;

public class DecoratorPattern {

    interface A {
        void method();
    }

    abstract class Decorator implements A {
        private A a;

        public Decorator (A a) {
            this.a = a;
        }

        @Override
        public void method() {
            a.method();
        }
    }

    private void main() {
        A a = new A() {
            @Override
            public void method() {
                System.out.println("A");
            }
        };

        A decorator = new Decorator(a) {
            @Override
            public void method() {
                System.out.println("<decorator>");
                super.method();
                System.out.println("</decorator>");
            }
        };

        a.method();
        decorator.method();
    }
    public static void main(String[] args) {
        new DecoratorPattern().main();
    }
}
