package com.vfreiman.lessons._7_patterns.behavioral;

public class CommandPattern {

    interface Command {
        void execute();
    }
    private class Receiver {
        void start() {
            System.out.println("start");
        }
        void stop() {
            System.out.println("stop");
        }
    }

    private class User {
        private Command start;
        private Command stop;

        public User(Command start, Command stop) {
            this.start = start;
            this.stop = stop;
        }

        private void startReceiver() {
            start.execute();
        }

        public void stopReceiver() {
            stop.execute();
        }
    }

    private void main() {
        Receiver mainReceiver = new Receiver();

        Command commandStart = new Command() {
            private Receiver receiver = mainReceiver;

            @Override
            public void execute() {
                receiver.start();
            }
        };

        Command commandStop = new Command() {
            private Receiver receiver = mainReceiver;

            @Override
            public void execute() {
                receiver.stop();
            }
        };

        User user = new User(commandStart, commandStop);
        user.startReceiver();
        user.stopReceiver();
    }

    public static void main(String[] args) {
        new CommandPattern().main();
    }
}
