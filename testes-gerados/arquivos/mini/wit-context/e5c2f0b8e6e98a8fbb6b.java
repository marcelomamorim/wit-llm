/*
 * @test
 * @summary Deterministic tests for LinkedBlockingQueue.offer(E) covering null input and capacity limits
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testOfferNullThrowsNPE();
        testOfferReturnsFalseWhenFull();
        testOfferSucceedsWhenNotFull();
    }

    private static void testOfferNullThrowsNPE() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.offer(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfferReturnsFalseWhenFull() {
        final int capacity = 1;
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(capacity);
        boolean firstOffer = queue.offer("first");
        if (!firstOffer) {
            throw new RuntimeException("First offer should succeed when queue is empty");
        }
        boolean secondOffer = queue.offer("second");
        if (secondOffer) {
            throw new RuntimeException("Offer should return false when queue is full");
        }
    }

    private static void testOfferSucceedsWhenNotFull() {
        final int capacity = 2;
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(capacity);
        boolean firstOffer = queue.offer("one");
        boolean secondOffer = queue.offer("two");
        if (!firstOffer || !secondOffer) {
            throw new RuntimeException("Offers should succeed when queue is not full");
        }
    }
}
