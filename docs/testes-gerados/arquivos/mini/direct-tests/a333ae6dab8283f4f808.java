/*
 * @test
 * @summary Test LinkedBlockingQueue.peek() method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPeekEmptyQueue();
        testPeekNonEmptyQueue();
    }

    static void testPeekEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // peek on empty queue must return null
        if (queue.peek() != null) {
            throw new AssertionError("peek() on empty queue should return null");
        }
    }

    static void testPeekNonEmptyQueue() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String element = "test";
        queue.put(element); // put one element
        String peeked = queue.peek();
        if (!element.equals(peeked)) {
            throw new AssertionError("peek() should return the head element");
        }
        // peek should not remove the element
        String polled = queue.take();
        if (!element.equals(polled)) {
            throw new AssertionError("take() should return the same element as peeked");
        }
    }
}
