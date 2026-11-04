/*
 * @test
 * @summary Test constructor of LinkedBlockingQueue with valid and invalid capacities
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testValidCapacity();
        testZeroCapacity();
        testNegativeCapacity();
    }

    static void testValidCapacity() {
        int capacity = 10;
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(capacity);
        assert queue != null : "Queue should be instantiated";
        assert queue.remainingCapacity() == capacity : "Remaining capacity should match the initialized capacity";
    }

    static void testZeroCapacity() {
        try {
            new LinkedBlockingQueue<>(0);
            assert false : "Expected IllegalArgumentException for zero capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testNegativeCapacity() {
        try {
            new LinkedBlockingQueue<>(-5);
            assert false : "Expected IllegalArgumentException for negative capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
