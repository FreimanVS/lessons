package com.vfreiman.lessons._7_patterns.structural;

/**
 * Фасад — это структурный паттерн проектирования, который предоставляет
 * простой интерфейс к сложной системе классов, библиотеке или фреймворку.
 *
 * Фасад полезен, если вы используете какую-то сложную библиотеку со множеством подвижных частей,
 * но вам нужна только часть её возможностей.
 *
 * Когда вы звоните в магазин и делаете заказ по телефону,
 * сотрудник службы поддержки является вашим фасадом ко всем службам и отделам магазина.
 */
public class Facade2 {

    private void orderPizza() {
        final Pizzeria pizzeria = new Pizzeria();
        System.out.println("Client: Pizza ordered");
        pizzeria.cookPizza();
        pizzeria.deliverPizza();
        System.out.println("Client: Pizza delivered");
    }

    private static class Pizzeria {
        private void cookPizza() {
            System.out.println("Pizzeria: Pizza cooked");
        }
        private void deliverPizza() {
            System.out.println("Pizzeria: Pizza sent");
        }
    }

    public static void main(String[] args) {
        new Facade2().orderPizza();
    }
}
