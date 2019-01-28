package com.vfreiman.lessons._7_patterns.behavioral;

public class StrategyPattern {

    private class A {
        private Strategy strategy;
        public void setStrategy(Strategy strategy) {
            this.strategy = strategy;
        }
        public void executeStrategy() {
            strategy.method();
        }
    }

    private interface Strategy {
        void method();
    }

    private void main() {
        Strategy strategy1 = new Strategy() {
            @Override
            public void method() {
                System.out.println("Strategy1");
            }
        };

        Strategy strategy2 = new Strategy() {
            @Override
            public void method() {
                System.out.println("Strategy2");
            }
        };

        A a = new A();
        a.setStrategy(strategy1);
        a.executeStrategy();

        a.setStrategy(strategy2);
        a.executeStrategy();

        a.setStrategy(() -> System.out.println("Strategy3"));
        a.executeStrategy();
    }
    public static void main(String[] args) {
        new StrategyPattern().main();
    }
}
