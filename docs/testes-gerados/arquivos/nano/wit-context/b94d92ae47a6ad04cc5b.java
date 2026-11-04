/*
 * @test
 * @summary Test for ArrayBlockingQueue constructor with capacity and fairness.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    private static void testConstructorWithValidParameters() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10, true);
        assert queue != null : "Queue should be instantiated with valid parameters";
    }

    private static void testConstructorWithZeroCapacity() {
        try {
            new ArrayBlockingQueue<>(0, false);
            assert false : "Expected IllegalArgumentException for zero capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithNegativeCapacity() {
        try {
            new ArrayBlockingQueue<>(-5, false);
            assert false : "Expected IllegalArgumentException for negative capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
