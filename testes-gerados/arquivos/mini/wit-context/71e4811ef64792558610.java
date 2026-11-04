/*
 * @test
 * @summary Test PriorityBlockingQueue.remove(Object) method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();

        // Test remove on empty queue returns false
        if (queue.remove("missing")) {
            throw new AssertionError("remove returned true for missing element");
        }

        // Add elements and test remove returns true for present element
        queue.add("a");
        queue.add("b");
        if (!queue.remove("a")) {
            throw new AssertionError("remove returned false for present element");
        }

        // After removal, element should no longer be present
        if (queue.remove("a")) {
            throw new AssertionError("remove returned true for already removed element");
        }

        // Remove remaining element
        if (!queue.remove("b")) {
            throw new AssertionError("remove returned false for present element");
        }

        // Queue should be empty now
        if (!queue.isEmpty()) {
            throw new AssertionError("queue not empty after removals");
        }

        System.out.println("PASS");
    }
}
