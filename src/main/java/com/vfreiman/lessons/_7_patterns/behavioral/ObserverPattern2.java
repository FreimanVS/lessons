package com.vfreiman.lessons._7_patterns.behavioral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Наблюдатель — это поведенческий паттерн проектирования, который создаёт механизм подписки,
 * позволяющий одним объектам следить и реагировать на события, происходящие в других объектах.
 */
public class ObserverPattern2 {
    private static abstract interface Subscriber {
        public abstract void update(String eventType, String message);
    }

    private static class Publisher {
        private Map<String, List<Subscriber>> subscribers = new HashMap<>();


        public Publisher(String... operations) {
            for (String operation : operations) {
                this.subscribers.put(operation, new ArrayList<>());
            }
        }

        private void subscribe(String eventType, Subscriber subscriber) {
            List<Subscriber> subscribers = this.subscribers.get(eventType);
            subscribers.add(subscriber);
        }

        private void unsubscribe(String eventType, Subscriber subscriber) {
            List<Subscriber> subscribers = this.subscribers.get(eventType);
            subscribers.remove(subscriber);
        }

        private void notify(String eventType, String message) {
            List<Subscriber> subscribers = this.subscribers.get(eventType);
            subscribers.forEach(subscriber -> subscriber.update(eventType, message));
        }
    }

    private static class Editor {
        private Publisher publisher;
        private String message;

        private Editor() {
            this.publisher = new Publisher("created", "changed");
        }

        private void create(String message) {
            this.message = message;
            publisher.notify("created", message);
        }

        private void change(String message) {
            this.message = message;
            publisher.notify("changed", message);
        }
    }

    private void main() {
        final Editor editor = new Editor();

        editor.publisher.subscribe("created", new Subscriber() {
            @Override
            public void update(String eventType, String message) {
                System.out.println("Create listener; eventType: " + eventType + "; message: " + message);
            }
        });

        editor.publisher.subscribe("changed", new Subscriber() {
            @Override
            public void update(String eventType, String message) {
                System.out.println("Change listener; eventType: " + eventType + "; message: " + message);
            }
        });

        editor.create("we created it");
        editor.change("we changed it");
    }
    public static void main(String[] args) {
        new ObserverPattern2().main();
    }
}
