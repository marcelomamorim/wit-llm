/*
 * @test
 * @summary Test for ArrayBlockingQueue.clear() method, focusing on normal behavior.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearEmptyQueue();
        testClearNonEmptyQueue();
    }

    private static void testClearEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.clear(); // Should not throw, even if empty
        System.out.println("testClearEmptyQueue passed");
    }

    private static void testClearNonEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.clear();
        if (queue.isEmpty()) {
            System.out.println("testClearNonEmptyQueue passed");
        } else {
            throw new AssertionError("Queue not empty after clear");
        }
    }
}
