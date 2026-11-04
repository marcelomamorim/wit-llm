/*
 * @test
 * @summary Test PriorityBlockingQueue.add(E) for null input and normal usage
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testAddNullThrowsNPE();
        testAddNonNullReturnsTrue();
    }

    private static void testAddNullThrowsNPE() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        try {
            queue.add(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAddNonNullReturnsTrue() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        boolean added = queue.add("test");
        if (!added) {
            throw new Error("add(E) should return true when adding a non-null element");
        }
        String polled = queue.poll();
        if (!"test".equals(polled)) {
            throw new Error("Element added was not found in the queue");
        }
    }
}
