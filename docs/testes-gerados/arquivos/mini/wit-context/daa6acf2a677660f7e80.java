/*
 * @test
 * @summary Test LinkedTransferQueue.add(E) for null argument and normal usage
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test normal add
        boolean added = queue.add("test");
        if (!added) {
            throw new AssertionError("add should return true when adding non-null element");
        }

        // Test that add throws NullPointerException when null is added
        try {
            queue.add(null);
            throw new AssertionError("add should throw NullPointerException when adding null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
