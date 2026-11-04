/*
 * @test
 * @summary Test LinkedTransferQueue.put(E) for null argument and normal usage
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test normal usage: put a non-null element
        String element = "test";
        queue.put(element);
        String polled = queue.poll();
        if (!element.equals(polled)) {
            throw new AssertionError("Expected element to be retrieved after put");
        }

        // Test null argument: put(null) should throw NullPointerException
        boolean npeThrown = false;
        try {
            queue.put(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when putting null");
        }

        System.out.println("LinkedTransferQueue.put(E) tests passed");
    }
}
