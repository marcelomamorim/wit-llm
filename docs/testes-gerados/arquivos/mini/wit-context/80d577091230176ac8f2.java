/*
 * @test
 * @summary Test ConcurrentLinkedQueue.Itr.next() method for expected NoSuchElementException
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testNextThrowsNoSuchElementException();
        testNextReturnsElementsInOrder();
    }

    private static void testNextThrowsNoSuchElementException() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        Iterator<String> it = queue.iterator();
        // Iterator on empty queue should throw NoSuchElementException on next()
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }

    private static void testNextReturnsElementsInOrder() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        Iterator<String> it = queue.iterator();

        // next() should return elements in FIFO order
        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected first element 'one', got: " + first);
        }

        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected second element 'two', got: " + second);
        }

        String third = it.next();
        if (!"three".equals(third)) {
            throw new AssertionError("Expected third element 'three', got: " + third);
        }

        // After all elements are consumed, next() should throw NoSuchElementException
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException after consuming all elements");
        }
    }
}
