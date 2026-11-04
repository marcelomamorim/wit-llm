/*
 * @test
 * @summary Test java.util.concurrent.ArrayBlockingQueue.Itr.next() method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNextThrowsNoSuchElementException();
        testNextReturnsElementAndAdvances();
    }

    private static void testNextThrowsNoSuchElementException() {
        // Create a queue with capacity 1 and empty
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        Iterator<String> it = queue.iterator();

        // The iterator has no elements, next() should throw NoSuchElementException
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testNextReturnsElementAndAdvances() {
        // Create a queue with capacity 2 and add one element
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("element1");
        Iterator<String> it = queue.iterator();

        // next() should return the first element
        String first = it.next();
        if (!"element1".equals(first)) {
            throw new AssertionError("Expected 'element1' but got: " + first);
        }

        // After consuming the only element, next() should throw NoSuchElementException
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException after consuming all elements");
        } catch (NoSuchElementException expected) {
            // Expected
        }
    }
}
