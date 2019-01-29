package com.vfreiman.lessons._7_patterns.structural;

/**
 * Декоратор — это структурный паттерн проектирования, который позволяет динамически добавлять объектам новую
 * функциональность, оборачивая их в полезные «обёртки».
 */
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
                System.out.print("A");
            }
        };

        A decorator1 = new Decorator(a) {
            @Override
            public void method() {
                System.out.print("<decorator>");
                super.method();
                System.out.print("</decorator>");
            }
        };

        A decorator2 = new Decorator(decorator1) {
            @Override
            public void method() {
                System.out.print("<main>");
                super.method();
                System.out.print("</main>");
            }
        };

        a.method();
        System.out.println("\r\n==========================================");
        decorator1.method();
        System.out.println("\r\n==========================================");
        decorator2.method();
    }
    public static void main(String[] args) {
        new DecoratorPattern().main();
    }
}
