/*
 * @test
 * @summary Tests the transfer method of LinkedTransferQueue for correct behavior.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testTransferWithElement();
        testTransferWithNullElement();
    }

    private static void testTransferWithElement() throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        Thread producer = new Thread(() -> {
            try {
                queue.transfer("test-element");
            } catch (InterruptedException e) {
                // ignore for test
            }
        });
        producer.start();
        // Wait briefly to ensure transfer is waiting
        Thread.sleep(50);
        // At this point, the transfer should be blocking, so queue should be empty
        assert queue.isEmpty() : "Queue should be empty while transfer is waiting";
        // Remove the element to unblock transfer
        String polled = queue.take();
        assert "test-element".equals(polled) : "Transferred element should be 'test-element'";
        producer.join();
    }

    private static void testTransferWithNullElement() throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        Thread producer = new Thread(() -> {
            try {
                queue.transfer(null);
            } catch (InterruptedException e) {
                // ignore for test
            }
        });
        producer.start();
        Thread.sleep(50);
        assert queue.isEmpty() : "Queue should be empty while transfer of null is waiting";
        String polled = queue.take();
        assert polled == null : "Transferred element should be null";
        producer.join();
    }
}
