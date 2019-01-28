package com.vfreiman.lessons._7_patterns.creational;

import java.util.Objects;

/**
 * Фабричный метод — это порождающий паттерн проектирования, который определяет общий интерфейс для создания объектов
 * в суперклассе, позволяя подклассам изменять тип создаваемых объектов.
 */
public class FactoryMethod {
    private static abstract interface Fruit {
        public abstract String getName();
    }

    private static class FruitFactory {
        static Fruit getFruit(String type) {
            switch (type) {
                case "apple":
                    return new Fruit() {//apple
                        @Override
                        public String getName() {
                            return "apple";
                        }
                    };
                case "lemon":
                    return new Fruit() {//lemon
                        @Override
                        public String getName() {
                            return "lemon";
                        }
                    };
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        Fruit lemon = FruitFactory.getFruit("lemon");
        Fruit apple = FruitFactory.getFruit("apple");
        Fruit unknown = FruitFactory.getFruit("unknown");

        Objects.requireNonNull(lemon); System.out.println(lemon.getName());
        Objects.requireNonNull(apple); System.out.println(apple.getName());
        System.out.println(unknown);
    }
}
