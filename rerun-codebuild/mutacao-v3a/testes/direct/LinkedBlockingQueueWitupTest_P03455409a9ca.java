/*
 * @test
 * @summary Tests the offer method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest_P03455409a9ca
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedBlockingQueueWitupTest_P03455409a9ca {

    public static void main(String[] args) {
        testOfferAcceptsNonNullElement();
        testOfferNullThrowsNullPointerException();
        testOfferReturnsFalseWhenCapacityFull();
    }

    static void testOfferAcceptsNonNullElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean result = queue.offer("element");
        if (!result) {
            throw new AssertionError("Offer should return true for non-null element");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should not be empty after offering an element");
        }
    }

    static void testOfferNullThrowsNullPointerException() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.offer(null);
            throw new AssertionError("offer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }

    static void testOfferReturnsFalseWhenCapacityFull() {
        int capacity = 1;
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(capacity);
        boolean firstOffer = queue.offer("first");
        if (!firstOffer) {
            throw new AssertionError("First offer should succeed");
        }
        boolean secondOffer = queue.offer("second");
        if (secondOffer) {
            throw new AssertionError("Offer should return false when capacity is full");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after full capacity offer");
        }
    }
}
