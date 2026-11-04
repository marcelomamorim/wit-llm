/*
 * @test
 * @summary Test LinkedTransferQueue.transfer(E) for null argument and interrupt behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test 1: transfer(null) should throw NullPointerException
        boolean npeThrown = false;
        try {
            queue.transfer(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when transfer(null) called");
        }

        // Test 2: transfer with interrupt - simulate interrupt and check InterruptedException
        Thread testThread = new Thread(new Runnable() {
            public void run() {
                LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
                try {
                    // Interrupt current thread before calling transfer
                    Thread.currentThread().interrupt();
                    q.transfer("test");
                    throw new AssertionError("Expected InterruptedException due to interrupt");
                } catch (InterruptedException e) {
                    // Expected
                } finally {
                    // Clear interrupt status for cleanliness
                    Thread.interrupted();
                }
            }
        });

        testThread.start();
        testThread.join();
    }
}
