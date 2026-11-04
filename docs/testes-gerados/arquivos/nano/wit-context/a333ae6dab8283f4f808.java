/*
 * @test
 * @summary Test for LinkedBlockingQueue.peek() method.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPeekEmptyQueue();
        testPeekNonEmptyQueue();
    }

    private static void testPeekEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        Integer result = queue.peek();
        if (result != null) {
            throw new AssertionError("Expected null when peeking empty queue, but got: " + result);
        }
    }

    private static void testPeekNonEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        Integer result = queue.peek();
        if (result == null || result != 1) {
            throw new AssertionError("Expected 1 when peeking queue with one element, but got: " + result);
        }
        // Verify that peek does not remove the element
        Integer secondPeek = queue.peek();
        if (secondPeek == null || secondPeek != 1) {
            throw new AssertionError("Peek should not remove element, expected 1, got: " + secondPeek);
        }
    }
}
