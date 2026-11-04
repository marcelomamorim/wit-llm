/*
 * @test
 * @summary Test LinkedTransferQueue.transfer(E) method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        // Test transfer with a consumer thread that takes the element
        Thread consumer = new Thread(new Runnable() {
            public void run() {
                try {
                    String item = queue.take();
                    if (!"test".equals(item)) {
                        throw new RuntimeException("Unexpected item: " + item);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        consumer.start();

        // This should not block indefinitely because consumer is waiting
        queue.transfer("test");

        consumer.join();

        // Test transfer with no consumer: it should block until interrupted
        final Thread transferThread = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.transfer("noConsumer");
                    throw new RuntimeException("transfer() returned without interruption");
                } catch (InterruptedException e) {
                    // Expected interruption
                }
            }
        });

        transferThread.start();

        // Sleep briefly to ensure transferThread is blocked in transfer
        Thread.sleep(100);

        // Interrupt the transferThread to cause InterruptedException
        transferThread.interrupt();

        transferThread.join();
    }
}
