/*
 * @test
 * @summary Test ArrayBlockingQueue constructor with capacity
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        // Test with valid capacity
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        if (queue == null) {
            throw new RuntimeException("Constructor returned null");
        }

        // Test that capacity is respected by trying to add more elements than capacity
        for (int i = 0; i < 10; i++) {
            boolean added = queue.offer(i);
            if (!added) {
                throw new RuntimeException("Failed to add element " + i + " to queue");
            }
        }
        // The queue should be full now, offer should return false
        boolean added = queue.offer(11);
        if (added) {
            throw new RuntimeException("Offer succeeded when queue should be full");
        }

        // Test with zero capacity - should throw IllegalArgumentException
        try {
            new ArrayBlockingQueue<>(0);
            throw new RuntimeException("Expected IllegalArgumentException for zero capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with negative capacity - should throw IllegalArgumentException
        try {
            new ArrayBlockingQueue<>(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
