/*
 * @test
 * @summary Test LinkedTransferQueue.offer(E) for null argument and normal usage
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test offer with a valid non-null element
        boolean offered = queue.offer("test");
        if (!offered) {
            throw new AssertionError("offer should return true for non-null element");
        }

        // Test offer with null element - expect NullPointerException
        try {
            queue.offer(null);
            throw new AssertionError("offer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
