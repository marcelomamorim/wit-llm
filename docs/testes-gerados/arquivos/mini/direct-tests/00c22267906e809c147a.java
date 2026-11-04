/*
 * @test
 * @summary Test LinkedTransferQueue.tryTransfer(E) method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test tryTransfer with a non-null element returns false (no waiting consumer)
        boolean result = queue.tryTransfer("test");
        if (result) {
            throw new AssertionError("tryTransfer should return false when no consumer is waiting");
        }

        // Test tryTransfer with null element throws NullPointerException
        try {
            queue.tryTransfer(null);
            throw new AssertionError("tryTransfer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test tryTransfer returns true if a consumer is waiting
        final LinkedTransferQueue<String> q2 = new LinkedTransferQueue<>();
        Thread consumer = new Thread(new Runnable() {
            public void run() {
                try {
                    String received = q2.take();
                    if (!"hello".equals(received)) {
                        throw new AssertionError("Consumer received wrong element: " + received);
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError("Consumer interrupted");
                }
            }
        });
        consumer.start();

        // Give consumer thread time to start and block on take()
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Test interrupted");
        }

        boolean transferResult = q2.tryTransfer("hello");
        if (!transferResult) {
            throw new AssertionError("tryTransfer should return true when consumer is waiting");
        }

        try {
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Test interrupted during join");
        }
    }
}
