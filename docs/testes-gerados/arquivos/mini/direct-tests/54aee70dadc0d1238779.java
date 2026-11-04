/*
 * @test
 * @summary Test PriorityBlockingQueue constructor with initialCapacity
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        // Test with a positive initial capacity
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(10);
        if (queue == null) {
            throw new AssertionError("Queue should not be null");
        }

        // Test with initial capacity 1 (minimum allowed by PriorityQueue)
        PriorityBlockingQueue<Integer> queueMin = new PriorityBlockingQueue<>(1);
        if (queueMin == null) {
            throw new AssertionError("Queue with capacity 1 should not be null");
        }

        // Test with initial capacity 0 - expect IllegalArgumentException
        try {
            new PriorityBlockingQueue<>(0);
            throw new AssertionError("Expected IllegalArgumentException for capacity 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with negative initial capacity - expect IllegalArgumentException
        try {
            new PriorityBlockingQueue<>(-5);
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
