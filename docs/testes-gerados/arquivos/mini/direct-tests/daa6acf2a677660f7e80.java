/*
 * @test
 * @summary Test LinkedTransferQueue add(E e) method basic behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test adding a non-null element returns true
        boolean added = queue.add("test");
        if (!added) {
            throw new AssertionError("add() should return true when adding a non-null element");
        }

        // Test that the element is actually present in the queue
        String polled = queue.poll();
        if (!"test".equals(polled)) {
            throw new AssertionError("Element added should be retrievable by poll()");
        }

        // Test adding null element throws NullPointerException
        try {
            queue.add(null);
            throw new AssertionError("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
