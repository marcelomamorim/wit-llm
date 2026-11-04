/*
 * @test    @summary Test remainingCapacity() method of ArrayBlockingQueue
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemainingCapacity();
    }

    public static void testRemainingCapacity() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        int initialCapacity = queue.remainingCapacity();
        System.out.println("Initial remaining capacity: " + initialCapacity);
        if (initialCapacity != 5) {
            throw new AssertionError("Expected initial capacity to be 5, but was " + initialCapacity);
        }
        try {
            queue.put(1);
            int capacityAfterPut = queue.remainingCapacity();
            System.out.println("Remaining capacity after one put: " + capacityAfterPut);
            if (capacityAfterPut != 4) {
                throw new AssertionError("Expected remaining capacity to be 4 after one put, but was " + capacityAfterPut);
            }
        } catch (InterruptedException e) {
            // Should not happen in this test
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
