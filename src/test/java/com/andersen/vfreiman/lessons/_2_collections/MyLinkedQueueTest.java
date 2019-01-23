package com.andersen.vfreiman.lessons._2_collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyLinkedQueueTest {

    private Queue<String> myLinkedQueue;

    @Before
    public void before() {
        myLinkedQueue = new MyLinkedQueue<>();
    }

    @Test
    public void initialSize() {
        Assert.assertEquals(0, myLinkedQueue.size());
    }

    @Test
    public void offer() {
        String s = "test";
        myLinkedQueue.offer(s);
        Assert.assertEquals(s, myLinkedQueue.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void elementFromEmpty() {
        myLinkedQueue.element();
    }

    @Test
    public void peek() {
        String peek = myLinkedQueue.peek();
        Assert.assertNull(peek);

        String s = "test";
        myLinkedQueue.offer(s);
        Assert.assertEquals(s, myLinkedQueue.peek());
        Assert.assertEquals(1, myLinkedQueue.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromEmpty() {
        myLinkedQueue.remove();
    }

    @Test
    public void poll() {
        String s = "test";
        myLinkedQueue.offer(s);
        String poll = myLinkedQueue.poll();
        String poll2 = myLinkedQueue.poll();
        Assert.assertEquals(s, poll);
        Assert.assertTrue(myLinkedQueue.isEmpty());
        Assert.assertNull(poll2);
    }

    @Test
    public void subsequence() {
        myLinkedQueue.add("1");
        myLinkedQueue.add("4");
        myLinkedQueue.add("2");
        myLinkedQueue.add("3");
        myLinkedQueue.add("5");

        String poll1 = myLinkedQueue.poll();
        String poll2 = myLinkedQueue.poll();
        String poll3 = myLinkedQueue.poll();
        String poll4 = myLinkedQueue.poll();
        String poll5 = myLinkedQueue.poll();

        Assert.assertEquals("1", poll1);
        Assert.assertEquals("4", poll2);
        Assert.assertEquals("2", poll3);
        Assert.assertEquals("3", poll4);
        Assert.assertEquals("5", poll5);
    }

    @Test
    public void iterate() {
        myLinkedQueue.add("1");
        myLinkedQueue.add("2");
        myLinkedQueue.add("3");
        myLinkedQueue.add("4");
        myLinkedQueue.add("5");

        StringBuilder result = new StringBuilder();
        Iterator<String> iterator = myLinkedQueue.iterator();
        while(iterator.hasNext()) {
            String next = iterator.next();
            result.append(next);
        }

        Assert.assertEquals("12345", result.toString());
    }

    @Test
    public void sizeAfterOffering() {
        myLinkedQueue.offer("test");
        Assert.assertEquals(1, myLinkedQueue.size());
        myLinkedQueue.offer("test2");
        myLinkedQueue.offer("test3");
        Assert.assertEquals(3, myLinkedQueue.size());
    }

    @Test
    public void sizeAfterPolling() {
        myLinkedQueue.offer("test");
        myLinkedQueue.offer("test2");
        myLinkedQueue.offer("test3");
        myLinkedQueue.offer("test4");

        myLinkedQueue.poll();
        Assert.assertEquals(3, myLinkedQueue.size());
        myLinkedQueue.poll();
        myLinkedQueue.poll();
        Assert.assertEquals(1, myLinkedQueue.size());
    }

    @After
    public void after() {
        myLinkedQueue = null;
    }

}