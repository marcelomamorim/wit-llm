/*
 * @test
 * @summary Test LinkedBlockingQueue constructor with capacity argument
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testValidCapacity();
        testZeroCapacity();
        testNegativeCapacity();
    }

    private static void testValidCapacity() {
        int capacity = 10;
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(capacity);
        if (queue == null) throw new RuntimeException("Queue is null");
        // No direct getter for capacity, but no exception means success
    }

    private static void testZeroCapacity() {
        try {
            new LinkedBlockingQueue<>(0);
            throw new RuntimeException("Expected IllegalArgumentException for zero capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNegativeCapacity() {
        try {
            new LinkedBlockingQueue<>(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
