/*
 * @test
 * @summary Test PriorityBlockingQueue.clear() method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testClearEmptiesQueue();
        testClearOnNonEmptyQueue();
    }

    private static void testClearEmptiesQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        if (queue.size() != 0) {
            throw new AssertionError("Expected empty queue initially");
        }
        queue.clear();
        if (queue.size() != 0) {
            throw new AssertionError("Expected empty queue after clear");
        }
    }

    private static void testClearOnNonEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(5);
        queue.add(20);
        if (queue.size() != 3) {
            throw new AssertionError("Expected size 3 before clear");
        }
        queue.clear();
        if (queue.size() != 0) {
            throw new AssertionError("Expected size 0 after clear");
        }
        // After clear, queue should not contain any elements
        if (queue.poll() != null) {
            throw new AssertionError("Expected poll() to return null after clear");
        }
    }
}
