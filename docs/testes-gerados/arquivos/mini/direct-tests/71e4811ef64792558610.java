/*
 * @test
 * @summary Test PriorityBlockingQueue.remove(Object) method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
    }

    private static void testRemoveExistingElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        boolean removed = queue.remove("a");
        if (!removed) {
            throw new AssertionError("Expected to remove existing element 'a'");
        }
        if (queue.contains("a")) {
            throw new AssertionError("Element 'a' should no longer be in the queue");
        }
    }

    private static void testRemoveNonExistingElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        boolean removed = queue.remove("x");
        if (removed) {
            throw new AssertionError("Expected remove to return false for non-existing element 'x'");
        }
        if (!queue.contains("a")) {
            throw new AssertionError("Element 'a' should still be in the queue");
        }
    }

    private static void testRemoveNullElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        // remove(null) should return false, no exception expected
        boolean removed = queue.remove(null);
        if (removed) {
            throw new AssertionError("Expected remove(null) to return false");
        }
        if (!queue.contains("a")) {
            throw new AssertionError("Element 'a' should still be in the queue after remove(null)");
        }
    }
}
