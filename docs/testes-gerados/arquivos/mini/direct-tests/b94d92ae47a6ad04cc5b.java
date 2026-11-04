/*
 * @test
 * @summary Test ArrayBlockingQueue constructor with capacity and fairness
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testValidCapacityFairFalse();
        testValidCapacityFairTrue();
        testZeroCapacityThrows();
        testNegativeCapacityThrows();
    }

    static void testValidCapacityFairFalse() {
        ArrayBlockingQueue<Object> q = new ArrayBlockingQueue<>(1, false);
        if (q == null) throw new RuntimeException("Queue should not be null");
    }

    static void testValidCapacityFairTrue() {
        ArrayBlockingQueue<Object> q = new ArrayBlockingQueue<>(1, true);
        if (q == null) throw new RuntimeException("Queue should not be null");
    }

    static void testZeroCapacityThrows() {
        try {
            new ArrayBlockingQueue<>(0, true);
            throw new RuntimeException("Expected IllegalArgumentException for zero capacity");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testNegativeCapacityThrows() {
        try {
            new ArrayBlockingQueue<>(-1, false);
            throw new RuntimeException("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
        }
    }
}
