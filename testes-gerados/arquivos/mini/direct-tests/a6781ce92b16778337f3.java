/*
 * @test
 * @summary Test LinkedTransferQueue.put(E) method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test putting a non-null element
        String element = "test";
        queue.put(element);
        String polled = queue.poll();
        if (!element.equals(polled)) {
            throw new AssertionError("Expected element '" + element + "' but got '" + polled + "'");
        }

        // Test putting null element - should throw NullPointerException
        boolean npeThrown = false;
        try {
            queue.put(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when putting null element");
        }

        System.out.println("All tests passed.");
    }
}
