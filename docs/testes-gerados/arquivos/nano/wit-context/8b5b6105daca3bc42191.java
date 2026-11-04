/*
 * @test
 * @summary Test for LinkedTransferQueue.transfer(E e) method, including null input handling.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.lang.Thread;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testTransferWithNull();
        testTransferWithValidElement();
    }

    private static void testTransferWithNull() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.transfer(null);
        } catch (NullPointerException npe) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when transferring null element");
        }
    }

    private static void testTransferWithValidElement() throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        String element = "test";
        Thread t = new Thread(() -> {
            try {
                queue.transfer(element);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        // Wait briefly to ensure transfer is in progress
        Thread.sleep(50);
        if (!t.isAlive()) {
            throw new AssertionError("Thread should be blocked on transfer");
        }
        // Remove element to unblock transfer
        String polled = queue.poll();
        if (polled != null) {
            throw new AssertionError("Queue should be empty before transfer completes");
        }
        t.join(1000);
        if (t.isAlive()) {
            throw new AssertionError("Transfer thread did not complete");
        }
        // Verify that the element was transferred
        if (!queue.contains(element)) {
            throw new AssertionError("Element was not transferred successfully");
        }
    }
}
