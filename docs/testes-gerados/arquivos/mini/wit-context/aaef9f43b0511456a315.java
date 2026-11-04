/*
 * @test
 * @summary Test LinkedBlockingQueue.clear() method for expected behavior and exception paths
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testClearOnEmptyQueue();
        testClearOnNonEmptyQueue();
    }

    private static void testClearOnEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        // Clear on empty queue should not throw and leave queue empty
        queue.clear();
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after clear");
        }
    }

    private static void testClearOnNonEmptyQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.put(1);
        queue.put(2);
        queue.put(3);
        // Clear should remove all elements
        queue.clear();
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after clear");
        }
        // After clear, offer and take should work normally
        boolean offered = queue.offer(10, 1, TimeUnit.SECONDS);
        if (!offered) {
            throw new AssertionError("Offer failed after clear");
        }
        Integer val = queue.poll(1, TimeUnit.SECONDS);
        if (val == null || val != 10) {
            throw new AssertionError("Poll returned wrong value after clear");
        }
    }
}
