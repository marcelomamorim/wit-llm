/*
 * @test
 * @summary Tests for LinkedBlockingQueue methods, focusing on remove()
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullLastRet();
        testRemoveWithNonNullLastRet();
    }

    private static void testRemoveWithNullLastRet() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        // Simulate internal state: lastRet == null
        // Since lastRet is private, we test the public behavior: calling remove() when lastRet is null should throw IllegalStateException
        try {
            queue.remove();
            // If no exception, test fails
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static void testRemoveWithNonNullLastRet() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        // To set lastRet non-null, we need to add an element and get an iterator
        queue.add(1);
        // Use iterator to set lastRet
        var iterator = queue.iterator();
        // Move to first element
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Now, simulate calling remove() on the iterator, which internally calls the queue's remove()
        // Since the internal lastRet is private, we test the behavior by calling remove() after iterator.next()
        try {
            queue.remove();
            // After removal, queue should be empty
            if (!queue.isEmpty()) {
                throw new AssertionError("Queue not empty after remove()");
            }
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException", e);
        }
    }
}
