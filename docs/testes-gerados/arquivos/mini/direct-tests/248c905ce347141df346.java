/*
 * @test
 * @summary Test ArrayBlockingQueue iterator next() method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsNoSuchElementException();
    }

    static void testNextReturnsElementsInOrder() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Iterator<String> it = queue.iterator();

        // next() should return elements in FIFO order
        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected 'a' but got " + first);
        }

        String second = it.next();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected 'b' but got " + second);
        }

        String third = it.next();
        if (!"c".equals(third)) {
            throw new AssertionError("Expected 'c' but got " + third);
        }
    }

    static void testNextThrowsNoSuchElementException() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        Iterator<String> it = queue.iterator();
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
}
