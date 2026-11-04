/*
 * @test
 * @summary Test ArrayBlockingQueue.remainingCapacity() correctness
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyQueue();
        testPartiallyFilledQueue();
        testFullQueue();
    }

    static void testEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        int remaining = q.remainingCapacity();
        if (remaining != 5) {
            throw new AssertionError("Expected remainingCapacity 5 but got " + remaining);
        }
    }

    static void testPartiallyFilledQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.put(1);
        q.put(2);
        int remaining = q.remainingCapacity();
        if (remaining != 3) {
            throw new AssertionError("Expected remainingCapacity 3 but got " + remaining);
        }
    }

    static void testFullQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.put(1);
        q.put(2);
        q.put(3);
        int remaining = q.remainingCapacity();
        if (remaining != 0) {
            throw new AssertionError("Expected remainingCapacity 0 but got " + remaining);
        }
    }
}
