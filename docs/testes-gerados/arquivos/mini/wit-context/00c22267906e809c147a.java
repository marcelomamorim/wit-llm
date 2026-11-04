/*
 * @test
 * @summary Test LinkedTransferQueue.tryTransfer(E) for null argument and normal usage
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test tryTransfer with null argument - expect NullPointerException
        try {
            queue.tryTransfer(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test tryTransfer with a normal non-null element - expect false (no waiting consumer)
        boolean result = queue.tryTransfer("test");
        if (result) {
            throw new Error("tryTransfer returned true unexpectedly when no consumer is waiting");
        }

        // Add a consumer thread to take element and test tryTransfer returns true
        Thread consumer = new Thread(new Runnable() {
            public void run() {
                try {
                    String taken = queue.take();
                    if (!"test2".equals(taken)) {
                        throw new Error("Consumer took unexpected element: " + taken);
                    }
                } catch (InterruptedException e) {
                    throw new Error("Consumer interrupted", e);
                }
            }
        });

        consumer.start();

        // Give consumer thread a moment to start and wait
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new Error("Sleep interrupted", e);
        }

        // Now tryTransfer with element should return true because consumer is waiting
        boolean result2 = queue.tryTransfer("test2");
        if (!result2) {
            throw new Error("tryTransfer returned false unexpectedly when consumer is waiting");
        }

        // Wait for consumer to finish
        try {
            consumer.join();
        } catch (InterruptedException e) {
            throw new Error("Join interrupted", e);
        }
    }
}
