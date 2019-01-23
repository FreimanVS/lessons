package com.andersen.vfreiman.lessons._6_jmm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class HappensBefore {
    public volatile int i = 0;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    private void main() {
        final List<Thread> list = new ArrayList<>();
        IntStream.range(0, 30000).forEach((n) -> {
            Thread t = new Thread(() -> {
                myCAS2();
            });
            list.add(t);
        });
        list.forEach(t -> t.start());

        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(i);
    }

    public void myCAS2() {

        int var5;
        int var6;
        do {
            var5 = i;
            var6 = var5 + 1;
        } while(!this.compareAndSwapInt(var5));

        i = var6;
    }
    public boolean compareAndSwapInt(int var5) {
        int localI = i;
        return localI == var5;
    }

    public void myCAS() {
        int localI = getI();
        int newI = localI + 1;
        while (true) {
            if (localI == getI()) {
                setI(newI);
                return;
            } else {
                localI = getI();
                newI = localI + 1;
            }
        }
    }

    public static void main(String[] args) {
        new HappensBefore().main();
    }
}
