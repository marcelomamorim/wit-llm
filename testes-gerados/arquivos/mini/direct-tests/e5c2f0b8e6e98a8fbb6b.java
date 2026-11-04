/*
 * @test
 * @summary Test LinkedBlockingQueue.offer(E) method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testOfferNull();
        testOfferCapacity();
        testOfferNormal();
    }

    static void testOfferNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.offer(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOfferCapacity() throws Exception {
        final int capacity = 2;
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(capacity);
        boolean offered1 = queue.offer("one");
        boolean offered2 = queue.offer("two");
        boolean offered3 = queue.offer("three");

        if (!offered1) throw new RuntimeException("Failed to offer first element");
        if (!offered2) throw new RuntimeException("Failed to offer second element");
        if (offered3) throw new RuntimeException("Offer succeeded beyond capacity");

        String first = queue.take();
        if (!"one".equals(first)) throw new RuntimeException("First element mismatch");

        boolean offeredAfterTake = queue.offer("three");
        if (!offeredAfterTake) throw new RuntimeException("Offer failed after take freed space");
    }

    static void testOfferNormal() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean offered = queue.offer("test");
        if (!offered) throw new RuntimeException("Offer returned false unexpectedly");
        String polled = queue.take();
        if (!"test".equals(polled)) throw new RuntimeException("Polled element mismatch");
    }
}
