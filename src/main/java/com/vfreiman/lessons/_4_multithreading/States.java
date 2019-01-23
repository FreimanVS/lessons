package com.vfreiman.lessons._4_multithreading;

public class States {
    public static void main(String[] args) {
        Thread.State newState = Thread.State.NEW;
        Thread.State runnableState = Thread.State.RUNNABLE;
        Thread.State terminatedState = Thread.State.TERMINATED;
        Thread.State blockedState = Thread.State.BLOCKED; //waiting for a monitor
        Thread.State waitingState = Thread.State.WAITING; //Object.wait with no timeout, Thread.join with no timeout, LockSupport.park
        Thread.State timedWaitingState = Thread.State.TIMED_WAITING; //Thread.sleep, Object.wait with timeout, Thread.join with timeout
                                                                     //LockSupport.parkNanos, LockSupport.parkUntil

    }
}
