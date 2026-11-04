/*
 * @test
 * @summary Test PriorityBlockingQueue.clear() method for correct clearing behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testClearEmptiesQueue();
        testClearOnEmptyQueue();
    }

    private static void testClearEmptiesQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        if (queue.size() != 3) {
            throw new AssertionError("Setup failed: expected size 3 but was " + queue.size());
        }

        queue.clear();

        if (queue.size() != 0) {
            throw new AssertionError("After clear: expected size 0 but was " + queue.size());
        }

        if (!queue.isEmpty()) {
            throw new AssertionError("After clear: expected queue to be empty");
        }
    }

    private static void testClearOnEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        if (!queue.isEmpty()) {
            throw new AssertionError("Setup failed: expected empty queue");
        }

        queue.clear();

        if (!queue.isEmpty()) {
            throw new AssertionError("After clear on empty: expected queue to remain empty");
        }
    }
}
