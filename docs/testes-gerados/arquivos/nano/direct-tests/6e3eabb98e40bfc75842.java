/*
 * @test    @summary Test the remove() method of ConcurrentLinkedQueue
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemoveWithElement();
        testRemoveWithoutElement();
    }

    private static void testRemoveWithElement() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("element1");
        queue.add("element2");
        // Remove element from queue
        String removed = queue.poll();
        // Verify that the queue is not empty after removal
        if (removed == null || !queue.contains(removed)) {
            throw new AssertionError("Failed to remove element from queue");
        }
        // Remove remaining element
        queue.poll();
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after removing all elements");
        }
    }

    private static void testRemoveWithoutElement() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        // Attempt to remove from empty queue
        String result = queue.poll();
        if (result != null) {
            throw new AssertionError("Expected null when removing from empty queue");
        }
    }
}
