/*
 * @test
 * @summary Test offer method of LinkedBlockingQueue for basic functionality and null input
 * @run main LinkedBlockingQueueWitupTest_Pa106fe95f17b
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest_Pa106fe95f17b {

    public static void main(String[] args) {
        testOfferWithValidElement();
        testOfferWithNullElement();
        testOfferWhenFullCapacity();
    }

    static void testOfferWithValidElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        boolean result = queue.offer("element");
        if (!result) {
            throw new AssertionError("Offer should succeed with valid element");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after offer");
        }
    }

    static void testOfferWithNullElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.offer(null);
            throw new AssertionError("offer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }

    static void testOfferWhenFullCapacity() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        boolean firstOffer = queue.offer("first");
        if (!firstOffer) {
            throw new AssertionError("First offer should succeed");
        }
        boolean secondOffer = queue.offer("second");
        if (secondOffer) {
            throw new AssertionError("Offer should fail when capacity is full");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after attempting to offer when full");
        }
    }
}
