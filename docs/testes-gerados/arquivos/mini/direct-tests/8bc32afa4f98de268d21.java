/*
 * @test
 * @summary Test PriorityBlockingQueue.put(E) method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();

        // Test putting a normal element
        queue.put("test");
        if (!"test".equals(queue.peek())) {
            throw new AssertionError("Element 'test' not found after put");
        }

        // Test putting null element - should throw NullPointerException
        boolean npeThrown = false;
        try {
            queue.put(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("NullPointerException not thrown when putting null");
        }

        // Test putting multiple elements and verify ordering
        PriorityBlockingQueue<Integer> intQueue = new PriorityBlockingQueue<>();
        intQueue.put(5);
        intQueue.put(1);
        intQueue.put(3);

        int first = intQueue.poll();
        if (first != 1) {
            throw new AssertionError("Expected 1 as first element, got " + first);
        }

        int second = intQueue.poll();
        if (second != 3) {
            throw new AssertionError("Expected 3 as second element, got " + second);
        }

        int third = intQueue.poll();
        if (third != 5) {
            throw new AssertionError("Expected 5 as third element, got " + third);
        }
    }
}
