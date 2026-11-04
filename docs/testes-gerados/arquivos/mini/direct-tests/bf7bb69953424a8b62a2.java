/*
 * @test
 * @summary Test ArrayBlockingQueue.peek() method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPeekEmptyQueue();
        testPeekNonEmptyQueue();
    }

    static void testPeekEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        String result = queue.peek();
        if (result != null) {
            throw new AssertionError("peek() on empty queue should return null, but returned: " + result);
        }
    }

    static void testPeekNonEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        boolean offered = queue.offer("test");
        if (!offered) {
            throw new AssertionError("offer() failed unexpectedly");
        }
        String result = queue.peek();
        if (!"test".equals(result)) {
            throw new AssertionError("peek() should return the head element 'test', but returned: " + result);
        }
        // peek() should not remove the element
        String polled = queue.poll();
        if (!"test".equals(polled)) {
            throw new AssertionError("poll() should return the head element 'test', but returned: " + polled);
        }
        // After poll, queue should be empty
        if (queue.peek() != null) {
            throw new AssertionError("peek() after poll() on empty queue should return null");
        }
    }
}
