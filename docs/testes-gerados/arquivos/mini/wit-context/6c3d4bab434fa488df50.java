/*
 * @test
 * @summary Test ArrayBlockingQueue(int capacity) constructor for valid and invalid capacity
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testValidCapacity();
        testInvalidCapacity();
    }

    private static void testValidCapacity() {
        // Test with a positive capacity
        int capacity = 10;
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(capacity);
        if (queue == null) {
            throw new AssertionError("Constructor returned null for valid capacity");
        }
        // The queue should be empty initially
        if (queue.size() != 0) {
            throw new AssertionError("Newly created queue should be empty");
        }
    }

    private static void testInvalidCapacity() {
        // Test with zero capacity
        try {
            new ArrayBlockingQueue<>(0);
            throw new AssertionError("Expected IllegalArgumentException for zero capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with negative capacity
        try {
            new ArrayBlockingQueue<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
