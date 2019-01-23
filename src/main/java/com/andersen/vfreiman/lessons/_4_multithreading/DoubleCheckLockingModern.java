package com.andersen.vfreiman.lessons._4_multithreading;

public class DoubleCheckLockingModern {
    private static class SingletonHolder {
        static final Singleton instance = new Singleton();
    }

    public static class Singleton {
        private Singleton() {
            
        }

        public static Singleton getInstance() {
            return SingletonHolder.instance;
        }
    }
}
