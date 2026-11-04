/*
 * @test
 * @summary Test ArrayBlockingQueue.clear() method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testClearOnEmptyQueue();
        testClearOnNonEmptyQueue();
    }

    private static void testClearOnEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        // Clear on empty queue should not throw and leave queue empty
        queue.clear();
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after clear on empty queue");
        }
    }

    private static void testClearOnNonEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        // Add elements
        queue.add(1);
        queue.add(2);
        queue.add(3);
        if (queue.size() != 3) {
            throw new AssertionError("Queue size should be 3 after adding elements");
        }
        // Clear should remove all elements
        queue.clear();
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after clear on non-empty queue");
        }
        // After clear, adding elements should work normally
        queue.add(4);
        if (queue.size() != 1 || queue.peek() == null || queue.peek() != 4) {
            throw new AssertionError("Queue should accept new elements after clear");
        }
    }
}
