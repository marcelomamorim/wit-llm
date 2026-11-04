/*
 * @test    @summary Test for ArrayBlockingQueue.remainingCapacity() method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemainingCapacityEmpty();
        testRemainingCapacityFull();
        testRemainingCapacityAfterAdd();
    }

    private static void testRemainingCapacityEmpty() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        int capacity = queue.remainingCapacity();
        if (capacity != 10) {
            throw new AssertionError("Expected remaining capacity 10, but got " + capacity);
        }
    }

    private static void testRemainingCapacityFull() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.offer(1);
        queue.offer(2);
        int capacity = queue.remainingCapacity();
        if (capacity != 0) {
            throw new AssertionError("Expected remaining capacity 0, but got " + capacity);
        }
    }

    private static void testRemainingCapacityAfterAdd() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.offer(1);
        queue.offer(2);
        int capacityBefore = queue.remainingCapacity();
        if (capacityBefore != 3) {
            throw new AssertionError("Expected remaining capacity 3 before adding, but got " + capacityBefore);
        }
        queue.offer(3);
        int capacityAfter = queue.remainingCapacity();
        if (capacityAfter != 2) {
            throw new AssertionError("Expected remaining capacity 2 after adding, but got " + capacityAfter);
        }
    }
}
