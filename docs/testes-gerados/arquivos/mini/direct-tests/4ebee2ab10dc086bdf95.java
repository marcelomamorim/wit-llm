/*
 * @test
 * @summary Test PriorityBlockingQueue.next() method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.Iterator;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInIteration();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextReturnsElementsInIteration() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Iterator<String> it = queue.iterator();

        // The iterator's next() should return elements until exhausted
        int count = 0;
        while (it.hasNext()) {
            String s = it.next();
            if (!(s.equals("a") || s.equals("b") || s.equals("c"))) {
                throw new AssertionError("Unexpected element: " + s);
            }
            count++;
        }
        if (count != 3) {
            throw new AssertionError("Expected 3 elements, got " + count);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
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
