package com.vfreiman.lessons._7_patterns.creational;

import java.util.Objects;

public class AbstractFactoryPattern {

    private static abstract interface Fruit {}

    private static abstract interface Picker {
        public abstract void pick(Fruit fruit);
    }

    private static abstract interface AbstractFactory {
        public abstract Fruit getFruit();
        public abstract Picker getPicker();
    }

    private static class Client {

        Fruit concreteFruit;
        Picker concretePicker;

        Client(AbstractFactory factory) {
            concreteFruit = factory.getFruit();
            concretePicker = factory.getPicker();
        }

        void run() {
            concretePicker.pick(concreteFruit);
        }
    }

    public static void main(String[] args) {
        final String type = "apple";
//        final String type = "orange";
//        final String type = "something";
        final Client client = configure(type);

        Objects.requireNonNull(client);
        client.run();
    }

    private static Client configure(String type) {
        switch (type) {
            case "apple":
                AbstractFactory appleFactory = new AbstractFactory() {
                    @Override
                    public Fruit getFruit() {
                        return new Fruit() { //apple

                        };
                    }

                    @Override
                    public Picker getPicker() {
                        return new Picker() { //apple picker
                            @Override
                            public void pick(Fruit fruit) {
                                System.out.println(this + " picked apple " + fruit);
                            }
                        };
                    }
                };
                return new Client(appleFactory);

            case "orange":
                AbstractFactory orangeFactory = new AbstractFactory() {
                    @Override
                    public Fruit getFruit() {
                        return new Fruit() {//orange
                        };
                    }

                    @Override
                    public Picker getPicker() {
                        return new Picker() {//orange picker
                            @Override
                            public void pick(Fruit fruit) {
                                System.out.println(this + " picked orange " + fruit);
                            }
                        };
                    }
                };
                return new Client(orangeFactory);

            default:
                return null;
        }
    }
}
