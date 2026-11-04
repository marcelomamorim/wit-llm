/*
 * @test
 * @summary Test PriorityBlockingQueue(int initialCapacity) constructor for valid and invalid inputs
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testInvalidInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Test with valid initial capacity
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(1);
        if (queue == null) {
            throw new AssertionError("Constructor returned null for valid initialCapacity");
        }
        // Test with larger initial capacity
        PriorityBlockingQueue<Integer> queueLarge = new PriorityBlockingQueue<>(10);
        if (queueLarge == null) {
            throw new AssertionError("Constructor returned null for valid initialCapacity 10");
        }
    }

    private static void testInvalidInitialCapacity() {
        // According to source and WIT, initialCapacity < 1 throws IllegalArgumentException
        try {
            new PriorityBlockingQueue<>(0);
            throw new AssertionError("Expected IllegalArgumentException for initialCapacity=0");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new PriorityBlockingQueue<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for initialCapacity=-1");
        } catch (IllegalArgumentException expected) {
        }
    }
}
