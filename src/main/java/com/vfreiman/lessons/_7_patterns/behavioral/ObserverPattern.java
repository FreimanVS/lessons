package com.vfreiman.lessons._7_patterns.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * Наблюдатель — это поведенческий паттерн проектирования, который создаёт механизм подписки,
 * позволяющий одним объектам следить и реагировать на события, происходящие в других объектах.
 */
public class ObserverPattern {
    private static abstract interface Subject {
        public abstract void register(Observer obj);
        public abstract void unregister(Observer obj);
        public abstract void notifyObservers();
    }

    public static abstract interface Observer {
        public abstract void handle();
    }

    private void main() {
        Subject subject = new Subject() {

            private final List<Observer> observers = new ArrayList<>();

            @Override
            public void register(Observer obj) {
                observers.add(obj);
            }

            @Override
            public void unregister(Observer obj) {
                observers.remove(obj);
            }

            @Override
            public void notifyObservers() {
                observers.forEach((observer) -> {
                    observer.handle();
                });
            }
        };

        Observer observer1 = new Observer() {
            @Override
            public void handle() {
                System.out.println("observer1's handle");
            }
        };

        Observer observer2 = new Observer() {
            @Override
            public void handle() {
                System.out.println("observer2's handle");
            }
        };

        Observer observer3 = new Observer() {
            @Override
            public void handle() {
                System.out.println("observer3's handle");
            }
        };

        subject.register(observer1);
        subject.register(observer2);
        subject.register(observer3);
        subject.notifyObservers();

        System.out.println("===========");
        subject.unregister(observer2);
        subject.notifyObservers();
    }

    public static void main(String[] args) {
        new ObserverPattern().main();
    }
}
