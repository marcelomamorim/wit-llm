/*
 * @test
 * @summary Test for PriorityBlockingQueue.next() method, focusing on NoSuchElementException.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNextThrowsWhenEmpty();
        testNextReturnsElementWhenAvailable();
    }

    private static void testNextThrowsWhenEmpty() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty queue");
        }
    }

    private static void testNextReturnsElementWhenAvailable() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(42);
        Integer result = queue.next();
        if (result == null || result != 42) {
            throw new AssertionError("Expected next() to return 42, but got: " + result);
        }
        // After calling next(), cursor should advance; calling again should throw if empty
        boolean exceptionThrown = false;
        try {
            queue.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException after consuming the only element");
        }
    }
}
