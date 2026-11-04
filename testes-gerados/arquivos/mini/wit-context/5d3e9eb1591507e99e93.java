/*
 * @test
 * @summary Test PriorityBlockingQueue.offer(E e, long timeout, TimeUnit unit) for null and normal elements
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();

        // Test that offer with null throws NullPointerException
        boolean npeThrown = false;
        try {
            queue.offer(null, 1L, TimeUnit.SECONDS);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when offering null element");
        }

        // Test that offer with a non-null element returns true
        boolean offered = queue.offer("test", 1L, TimeUnit.SECONDS);
        if (!offered) {
            throw new AssertionError("Expected offer to return true for non-null element");
        }

        // Test that the element is actually in the queue
        String polled = queue.poll();
        if (!"test".equals(polled)) {
            throw new AssertionError("Expected to poll the element 'test' from the queue");
        }
    }
}
