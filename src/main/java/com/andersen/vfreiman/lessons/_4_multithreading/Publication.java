package com.andersen.vfreiman.lessons._4_multithreading;

/**
 * #mojet vibrosi'tsya isklu4enie, esli odin potok initialize(), a drugoi - assertSanity();
 *      vtoroi mojet poluchit' neproinicializirovannuu peremennuyu n, a pri vizove methoda ona
 * #budet uje proinicializirovannoi;
 * #sposobi ispravleniya: variables have to be local, or final, or volatile, or Locked by a Locker.
 */
public class Publication {
    private Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }

    private static class Holder {
        private int n;

        public Holder(int n) {
            this.n = n;
        }

        public void assertSanity() {
            if (n != n) {
                throw new AssertionError("This statement is false");
            }
        }
    }
}
