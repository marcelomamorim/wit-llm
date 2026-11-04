/*
 * @test
 * @summary Test PriorityBlockingQueue.offer(E e, long timeout, TimeUnit unit) delegates to offer(E e)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();

        // Test normal offer returns true
        boolean offered = queue.offer("test");
        if (!offered) {
            throw new AssertionError("offer(E e) should return true for non-null element");
        }

        // Test offer with timeout delegates to offer(E e)
        boolean offeredWithTimeout = queue.offer("testTimeout", 1000L, TimeUnit.MILLISECONDS);
        if (!offeredWithTimeout) {
            throw new AssertionError("offer(E e, long, TimeUnit) should return true for non-null element");
        }

        // Test that offer with null element throws NullPointerException
        boolean npeThrown = false;
        try {
            queue.offer(null, 1000L, TimeUnit.MILLISECONDS);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("offer(E e, long, TimeUnit) should throw NullPointerException for null element");
        }

        // Test that offer(E e) throws NullPointerException for null element
        npeThrown = false;
        try {
            queue.offer(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("offer(E e) should throw NullPointerException for null element");
        }
    }
}
