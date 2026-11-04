/*
 * @test
 * @summary Test LinkedTransferQueue.offer(E) method basic behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test offer with a normal string
        boolean offered = queue.offer("test");
        if (!offered) throw new AssertionError("offer should return true");

        // The queue should contain the offered element
        String polled = queue.poll();
        if (!"test".equals(polled)) throw new AssertionError("polled element should be 'test'");

        // Test offer with null - should throw NullPointerException as per Collection contract
        boolean npeThrown = false;
        try {
            queue.offer(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) throw new AssertionError("offer(null) should throw NullPointerException");
    }
}
