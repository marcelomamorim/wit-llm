/*
 * @test
 * @summary Testa o método size() de PriorityBlockingQueue
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testSizeEmptyQueue();
        testSizeAfterAdd();
        testSizeAfterRemove();
    }

    static void testSizeEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        int size = queue.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0, but got: " + size);
        }
    }

    static void testSizeAfterAdd() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        int size = queue.size();
        if (size != 1) {
            throw new AssertionError("Expected size 1 after add, but got: " + size);
        }
    }

    static void testSizeAfterRemove() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.remove();
        int size = queue.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0 after remove, but got: " + size);
        }
    }
}
