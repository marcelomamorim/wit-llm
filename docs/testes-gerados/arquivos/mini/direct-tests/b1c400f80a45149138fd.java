/*
 * @test
 * @summary Test LinkedBlockingQueue iterator basic behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyQueueIterator();
        testSingleElementIterator();
        testMultipleElementsIterator();
        testIteratorNoSuchElementException();
    }

    static void testEmptyQueueIterator() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Iterator<String> it = queue.iterator();
        if (it.hasNext()) {
            throw new AssertionError("Iterator of empty queue should have no next element");
        }
    }

    static void testSingleElementIterator() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        Iterator<String> it = queue.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String val = it.next();
        if (!"one".equals(val)) {
            throw new AssertionError("Iterator returned wrong element: " + val);
        }
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no more elements");
        }
    }

    static void testMultipleElementsIterator() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Iterator<Integer> it = queue.iterator();
        int expected = 1;
        while (it.hasNext()) {
            int val = it.next();
            if (val != expected) {
                throw new AssertionError("Expected " + expected + " but got " + val);
            }
            expected++;
        }
        if (expected != 4) {
            throw new AssertionError("Iterator did not iterate all elements");
        }
    }

    static void testIteratorNoSuchElementException() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Iterator<String> it = queue.iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Calling next() on empty iterator should throw NoSuchElementException");
        }
    }
}
