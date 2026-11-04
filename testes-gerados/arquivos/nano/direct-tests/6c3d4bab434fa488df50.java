/*
 * @test    @summary Test ArrayBlockingQueue constructor with capacity.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    static void testConstructorWithValidCapacity() {
        int capacity = 10;
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(capacity);
        assert queue != null : "Queue should be instantiated";
        assert queue.size() == 0 : "Queue should be empty";
    }

    static void testConstructorWithZeroCapacity() {
        int capacity = 0;
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(capacity);
        assert queue != null : "Queue should be instantiated";
        assert queue.size() == 0 : "Queue should be empty";
        try {
            queue.offer(1);
            assert queue.size() == 0 : "Offer should not add element when capacity is zero";
        } catch (Exception e) {
            assert false : "No exception expected when offering to zero capacity queue";
        }
    }

    static void testConstructorWithNegativeCapacity() {
        int capacity = -1;
        try {
            new ArrayBlockingQueue<>(capacity);
            assert false : "Expected IllegalArgumentException for negative capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
