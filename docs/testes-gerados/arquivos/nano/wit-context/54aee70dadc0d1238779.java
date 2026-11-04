/* @test    @summary Test PriorityBlockingQueue constructor with initialCapacity parameter    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    private static void testConstructorWithValidCapacity() {
        int capacity = 10;
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(capacity);
        assert queue != null : "Queue should be instantiated";
        assert queue.size() == 0 : "Queue should be empty";
    }

    private static void testConstructorWithZeroCapacity() {
        try {
            PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(0);
            assert false : "Expected IllegalArgumentException for zero capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithNegativeCapacity() {
        try {
            PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(-5);
            assert false : "Expected IllegalArgumentException for negative capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
