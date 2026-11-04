/*
 * @test
 * @summary Test for ArrayBlockingQueue constructor with capacity, including invalid input.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testValidCapacity();
        testZeroCapacity();
        testNegativeCapacity();
    }

    static void testValidCapacity() {
        try {
            ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
            if (queue == null || queue.size() != 0) {
                throw new AssertionError("Queue should be initialized with size 0");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid capacity", e);
        }
    }

    static void testZeroCapacity() {
        try {
            ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(0);
            if (queue == null || queue.size() != 0) {
                throw new AssertionError("Queue with zero capacity should be initialized with size 0");
            }
        } catch (IllegalArgumentException e) {
            // Expected exception for zero capacity
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero capacity", e);
        }
    }

    static void testNegativeCapacity() {
        try {
            ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative capacity, but no exception was thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception for negative capacity
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative capacity", e);
        }
    }
}
