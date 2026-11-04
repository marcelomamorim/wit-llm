/*
 * @test
 * @summary Test ArrayBlockingQueue(int capacity, boolean fair) constructor for valid and invalid capacity
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
        System.out.println("All tests passed.");
    }

    private static void testConstructorWithValidCapacity() {
        // Test with positive capacity, fairness true and false
        ArrayBlockingQueue<Integer> q1 = new ArrayBlockingQueue<>(1, true);
        if (q1 == null) throw new AssertionError("Queue should not be null");

        ArrayBlockingQueue<Integer> q2 = new ArrayBlockingQueue<>(10, false);
        if (q2 == null) throw new AssertionError("Queue should not be null");
    }

    private static void testConstructorWithZeroCapacity() {
        try {
            new ArrayBlockingQueue<>(0, true);
            throw new AssertionError("Expected IllegalArgumentException for zero capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testConstructorWithNegativeCapacity() {
        try {
            new ArrayBlockingQueue<>(-1, false);
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
