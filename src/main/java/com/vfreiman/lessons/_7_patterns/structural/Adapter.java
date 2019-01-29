package com.vfreiman.lessons._7_patterns.structural;

/**
 * Адаптер — это структурный паттерн проектирования, который позволяет объектам с несовместимыми интерфейсами работать вместе.
 */
public class Adapter {
    private class Socket {
        public int getVolt(){
            return 220;
        }
    }

    private static abstract interface SocketAdapter {
        public abstract int get220Volt();
        public abstract int get22Volt();
        public abstract int get2Volt();
    }

    private void main() {
        SocketAdapter socketObjectAdapterImpl = new SocketAdapter() {
            private Socket sock = new Socket();

            @Override
            public int get220Volt() {
                return sock.getVolt();
            }

            @Override
            public int get22Volt() {
                return sock.getVolt()/10;
            }

            @Override
            public int get2Volt() {
                return sock.getVolt()/110;
            }
        };

        System.out.println(socketObjectAdapterImpl.get220Volt());
        System.out.println(socketObjectAdapterImpl.get22Volt());
        System.out.println(socketObjectAdapterImpl.get2Volt());
    }

    public static void main(String[] args) {
        new Adapter().main();
    }
}
