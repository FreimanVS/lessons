package com.vfreiman.lessons._7_patterns.behavioral;

/**
 * Вы заходите в ресторан и садитесь у окна. К вам подходит вежливый официант и принимает заказ,
 * записывая все пожелания в блокнот. Откланявшись, он уходит на кухню, где вырывает лист из блокнота и клеит на стену.
 * Далее лист оказывается в руках повара, который читает содержание заказа и готовит заказанные блюда.
 * В этом примере вы являетесь отправителем, официант с блокнотом — командой, а повар — получателем.
 * Как и в паттерне, вы не соприкасаетесь напрямую с поваром. Вместо этого вы отправляете заказ с официантом,
 * который самостоятельно «настраивает» повара на работу. С другой стороны, повар не знает,
 * кто конкретно послал ему заказ. Но это ему безразлично, так как вся необходимая информация есть в листе заказа.
 */
public class CommandPattern2 {

    private static abstract interface CommandToWaiter {
        public abstract void processOrder();
    }

    private static class ChefCooker {
        private void startCooking() {
            System.out.println("ChefCooker started cooking");
        }

        private void completeCooking() {
            System.out.println("ChefCooker completed cooking");
        }
    }

    private static class Client {

        private CommandToWaiter order;
        private CommandToWaiter takeOrder;

        private Client(CommandToWaiter order, CommandToWaiter takeOrder) {
            this.order = order;
            this.takeOrder = takeOrder;
        }

        private void order() {
            order.processOrder();
        }

        private void takeOrder() {
            takeOrder.processOrder();
        }
    }

    private void main() {
        ChefCooker chefCooker = new ChefCooker();

        CommandToWaiter order = new CommandToWaiter() {
            @Override
            public void processOrder() {
                System.out.println("Client ordered");
                chefCooker.startCooking();
            }
        };

        CommandToWaiter takeOrder = new CommandToWaiter() {
            @Override
            public void processOrder() {
                chefCooker.completeCooking();
                System.out.println("Client took an order");
            }
        };

        Client client = new Client(order, takeOrder);
        client.order();
        client.takeOrder();
    }
    public static void main(String[] args) {
        new CommandPattern2().main();
    }
}
