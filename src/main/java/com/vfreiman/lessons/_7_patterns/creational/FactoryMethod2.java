package com.vfreiman.lessons._7_patterns.creational;

/**
 * Фабричный метод — это порождающий паттерн проектирования, который определяет общий интерфейс для создания объектов
 * в суперклассе, позволяя подклассам изменять тип создаваемых объектов.
 * Имеет один метод произдводства.
 */

public class FactoryMethod2 {
    private static abstract interface Fruit {
        public abstract String getName();
    }

    private static abstract class FruitFactory {
        abstract Fruit getFruit();
    }

    private void main() {

        final String order = "apple";
//        final String order = "lemon";
//        final String order = "unknown";
        final FruitFactory fruitFactory = configure(order);

        Fruit fruit = fruitFactory.getFruit();
        System.out.println(fruit.getName());
    }

    private FruitFactory configure(String fruit) {
        switch(fruit) {
            case "lemon" :
                return new FruitFactory() {//lemon factory
                    @Override
                    Fruit getFruit() {
                        return new Fruit() {
                            @Override
                            public String getName() {
                                return "lemon";
                            }
                        };
                    }
                };

            case "apple":
                return new FruitFactory() {//apple factory
                    @Override
                    Fruit getFruit() {
                        return new Fruit() {
                            @Override
                            public String getName() {
                                return "apple";
                            }
                        };
                    }
                };

            default:

                return new FruitFactory() {//unknown factory
                    @Override
                    Fruit getFruit() {
                        return new Fruit() {
                            @Override
                            public String getName() {
                                return "unknown fruit";
                            }
                        };
                    }
                };
        }
    }

    public static void main(String[] args) {
        new FactoryMethod2().main();
    }
}
