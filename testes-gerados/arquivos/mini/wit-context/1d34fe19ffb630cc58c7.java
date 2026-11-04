/*
 * @test
 * @summary Test deterministic behavior of ArrayBlockingQueue.remainingCapacity()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        // Create a queue with capacity 3
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        // Initially, remainingCapacity should be equal to capacity (3)
        int remaining = queue.remainingCapacity();
        if (remaining != 3) {
            throw new AssertionError("Expected remainingCapacity 3 but got " + remaining);
        }

        // Add one element
        boolean added = queue.offer(1);
        if (!added) {
            throw new AssertionError("Failed to add element to queue");
        }

        // Now remainingCapacity should be 2
        remaining = queue.remainingCapacity();
        if (remaining != 2) {
            throw new AssertionError("Expected remainingCapacity 2 but got " + remaining);
        }

        // Add two more elements to fill the queue
        added = queue.offer(2);
        if (!added) {
            throw new AssertionError("Failed to add second element to queue");
        }
        added = queue.offer(3);
        if (!added) {
            throw new AssertionError("Failed to add third element to queue");
        }

        // Now remainingCapacity should be 0
        remaining = queue.remainingCapacity();
        if (remaining != 0) {
            throw new AssertionError("Expected remainingCapacity 0 but got " + remaining);
        }

        // Remove one element
        Integer polled = queue.poll();
        if (polled == null || polled != 1) {
            throw new AssertionError("Expected to poll 1 but got " + polled);
        }

        // Now remainingCapacity should be 1
        remaining = queue.remainingCapacity();
        if (remaining != 1) {
            throw new AssertionError("Expected remainingCapacity 1 but got " + remaining);
        }

        System.out.println("All tests passed.");
    }
}
