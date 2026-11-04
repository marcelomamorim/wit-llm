/*
 * @test
 * @summary Test LinkedBlockingQueue(int capacity) constructor for valid and invalid capacities
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testValidCapacity();
        testInvalidCapacity();
        System.out.println("PASS");
    }

    private static void testValidCapacity() {
        // Test with a positive capacity
        int capacity = 10;
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(capacity);
        if (queue == null) {
            throw new AssertionError("Queue instance is null");
        }
    }

    private static void testInvalidCapacity() {
        // Test with zero capacity - should throw IllegalArgumentException
        try {
            new LinkedBlockingQueue<>(0);
            throw new AssertionError("Expected IllegalArgumentException for capacity=0");
        } catch (IllegalArgumentException expected) {
        }

        // Test with negative capacity - should throw IllegalArgumentException
        try {
            new LinkedBlockingQueue<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for capacity=-1");
        } catch (IllegalArgumentException expected) {
        }
    }
}
