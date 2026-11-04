/*
 * @test    @summary Test the size() method of ArrayBlockingQueue for correctness.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testSizeOnEmptyQueue();
        testSizeAfterInsertions();
        testSizeAfterRemovals();
    }

    static void testSizeOnEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        int size = queue.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0, but got: " + size);
        }
    }

    static void testSizeAfterInsertions() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.offer(1);
        queue.offer(2);
        int size = queue.size();
        if (size != 2) {
            throw new AssertionError("Expected size 2, but got: " + size);
        }
    }

    static void testSizeAfterRemovals() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.offer(1);
        queue.offer(2);
        queue.poll();
        int size = queue.size();
        if (size != 1) {
            throw new AssertionError("Expected size 1, but got: " + size);
        }
    }
}
