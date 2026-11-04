/*
 * @test
 * @summary Tests the tryTransfer method of LinkedTransferQueue for correct behavior.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testTryTransferWithElement();
        testTryTransferWithNull();
        testTryTransferAfterClear();
    }

    static void testTryTransferWithElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean result = queue.tryTransfer("element");
        // Expect true if transfer succeeded, false otherwise
        // Since queue is empty, tryTransfer should succeed and return true
        if (!result) {
            throw new AssertionError("Expected tryTransfer to return true when queue is empty.");
        }
        // After transfer, queue should contain the element
        if (queue.peek() == null || !queue.peek().equals("element")) {
            throw new AssertionError("Queue should contain the transferred element.");
        }
    }

    static void testTryTransferWithNull() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean result = false;
        try {
            result = queue.tryTransfer(null);
        } catch (NullPointerException e) {
            // Expected if null elements are not allowed
            return;
        }
        // If null is accepted, result should be true or false, but queue should contain null
        if (result) {
            if (queue.peek() != null || !queue.contains(null)) {
                throw new AssertionError("Queue should contain null after tryTransfer(null).");
            }
        } else {
            // If tryTransfer returns false, queue should be empty
            if (queue.peek() != null) {
                throw new AssertionError("Queue should be empty if tryTransfer returns false.");
            }
        }
    }

    static void testTryTransferAfterClear() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.offer("test");
        queue.clear();
        boolean result = queue.tryTransfer("testAfterClear");
        // Expect true if transfer succeeded, false otherwise
        if (!result) {
            // Transfer may fail if no consumer is waiting; acceptable
            return;
        }
        // If transfer succeeded, queue should contain the element
        if (queue.peek() == null || !queue.peek().equals("testAfterClear")) {
            throw new AssertionError("Queue should contain the element after transfer.");
        }
    }
}
