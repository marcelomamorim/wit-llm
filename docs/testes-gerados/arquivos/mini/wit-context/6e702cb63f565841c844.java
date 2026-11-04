/*
 * @test
 * @summary Test LinkedTransferQueue.offer(E e, long timeout, TimeUnit unit) for null argument and normal usage
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test normal offer with non-null element
        boolean offered = queue.offer("test", 1000L, TimeUnit.MILLISECONDS);
        if (!offered) {
            throw new AssertionError("offer should return true for non-null element");
        }

        // Test offer with null element - expect NullPointerException
        try {
            queue.offer(null, 1000L, TimeUnit.MILLISECONDS);
            throw new AssertionError("offer(null, ...) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
