package com.vfreiman.lessons._7_patterns.behavioral;

public class Delegate {

    private static class A {
        public void execute() {
            System.out.println("A");
        }
    }

    private static class B {
        private A a;
        public void setA(A a) {
            this.a = a;
        }
        public void execute() {
            a.execute();
        }
    }

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        b.setA(a);
        b.execute();
    }
}
