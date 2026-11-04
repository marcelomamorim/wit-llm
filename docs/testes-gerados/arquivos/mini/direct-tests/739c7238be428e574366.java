/*
 * @test
 * @summary Test PriorityBlockingQueue.peek() method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testPeekEmptyQueue();
        testPeekNonEmptyQueue();
    }

    private static void testPeekEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        // peek on empty queue should return null
        if (queue.peek() != null) {
            throw new AssertionError("peek() on empty queue should return null");
        }
    }

    private static void testPeekNonEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(5);
        queue.add(20);
        Integer peeked = queue.peek();
        // peek should return the smallest element (5) without removing it
        if (peeked == null || peeked.intValue() != 5) {
            throw new AssertionError("peek() should return the smallest element 5, got: " + peeked);
        }
        // peek should not remove the element
        if (queue.size() != 3) {
            throw new AssertionError("peek() should not remove element, size should be 3");
        }
    }
}
