/*
 * @test
 * @summary Test basic behavior of ArrayBlockingQueue.poll() method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testPollEmptyQueueReturnsNull();
        testPollNonEmptyQueueReturnsElement();
    }

    private static void testPollEmptyQueueReturnsNull() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        String result = queue.poll();
        if (result != null) {
            throw new AssertionError("poll() on empty queue should return null, but returned: " + result);
        }
    }

    private static void testPollNonEmptyQueueReturnsElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        boolean offered = queue.offer("test");
        if (!offered) {
            throw new AssertionError("offer() should succeed on empty queue");
        }
        String result = queue.poll();
        if (!"test".equals(result)) {
            throw new AssertionError("poll() should return the offered element 'test', but returned: " + result);
        }
        // After poll, queue should be empty
        if (queue.poll() != null) {
            throw new AssertionError("poll() after removing the only element should return null");
        }
    }
}
