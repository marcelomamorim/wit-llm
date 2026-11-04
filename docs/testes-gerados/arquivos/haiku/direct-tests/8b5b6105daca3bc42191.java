/*
 * @test
 * @summary LinkedTransferQueue.transfer(E) must throw InterruptedException when the calling thread is interrupted with no consumer, and must block until a consumer takes the element
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testInterruptedTransferThrows();
        testTransferDeliveredToConsumer();
    }

    static void testInterruptedTransferThrows() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        Thread.currentThread().interrupt();
        try {
            q.transfer(1);
            throw new RuntimeException("expected InterruptedException");
        } catch (InterruptedException expected) {
            // expected
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new RuntimeException("interrupt status should be cleared");
        }
        if (!q.isEmpty()) {
            throw new RuntimeException("queue should be empty after interrupted transfer");
        }
    }

    static void testTransferDeliveredToConsumer() throws Exception {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        AtomicInteger received = new AtomicInteger(-1);
        Thread consumer = new Thread(() -> {
            try {
                received.set(q.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        consumer.start();
        q.transfer(42);
        consumer.join();
        if (received.get() != 42) {
            throw new RuntimeException("expected 42 but got " + received.get());
        }
        if (!q.isEmpty()) {
            throw new RuntimeException("queue should be empty after transfer completes");
        }
    }
}
