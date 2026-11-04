/*
 * @test
 * @summary Tests ArrayBlockingQueue constructor with capacity and fairness parameters.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    static void testConstructorWithValidParameters() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10, true);
        assert queue != null : "Queue should be instantiated";
        assert queue.size() == 0 : "Queue should be empty upon creation";
    }

    static void testConstructorWithZeroCapacity() {
        try {
            new ArrayBlockingQueue<>(0, false);
            assert false : "Expected IllegalArgumentException for zero capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testConstructorWithNegativeCapacity() {
        try {
            new ArrayBlockingQueue<>(-5, true);
            assert false : "Expected IllegalArgumentException for negative capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
