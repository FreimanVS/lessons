package com.vfreiman.lessons._2_collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {

    public static void main(String[] args) {

        BlockingQueue<DelayedElement> blockingQueue = new DelayQueue<DelayedElement>();

        try {
            blockingQueue
                    .put(new DelayedElement(4000, "Message with delay 4s"));
            blockingQueue
                    .put(new DelayedElement(2000, "Message with delay 2s"));
            blockingQueue
                    .put(new DelayedElement(9000, "Message with delay 9s"));
        } catch (InterruptedException ie) {
        }

        while (!blockingQueue.isEmpty()) {
            try {
                System.out.println(">>" + blockingQueue.take());
            } catch (InterruptedException ie) {
            }

        }

    }

    private static class DelayedElement implements Delayed {

        private long duration;
        private String message;

        public DelayedElement(long duration, String name) {
            this.duration = System.currentTimeMillis() + duration;
            this.message = name;
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.duration - ((DelayedElement) o).getDuration());
        }

        @Override
        /*
         * Expiration occurs when an element's getDelay(TimeUnit unit) method
         * returns a value less than or equal to zero.
         */
        public long getDelay(TimeUnit unit) {
            long diff = duration - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "DelayedElement [duration=" + duration + ", message=" + message
                    + "]";
        }

    }
}
