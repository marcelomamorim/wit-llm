/*
 * @test
 * @summary Testa o método size() de ArrayBlockingQueue para comportamento observável.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testSizeWithEmptyQueue();
        testSizeWithElements();
    }

    static void testSizeWithEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        int size = queue.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0, but got: " + size);
        }
    }

    static void testSizeWithElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.offer(1);
        queue.offer(2);
        int size = queue.size();
        if (size != 2) {
            throw new AssertionError("Expected size 2, but got: " + size);
        }
    }
}
