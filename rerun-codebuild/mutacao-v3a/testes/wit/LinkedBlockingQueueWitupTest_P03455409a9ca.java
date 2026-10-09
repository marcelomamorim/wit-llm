/*
 * @test
 * @summary Deterministic tests for LinkedBlockingQueue.offer behavior.
 * @run main LinkedBlockingQueueWitupTest_P03455409a9ca
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedBlockingQueueWitupTest_P03455409a9ca {

    public static void main(String[] args) {
        testOfferNullElement();
        testOfferNormalElement();
        testOfferCapacityFull();
    }

    private static void testOfferNullElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        boolean exceptionThrown = false;
        try {
            queue.offer(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }

    private static void testOfferNormalElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        boolean result = queue.offer("element");
        if (!result) {
            throw new AssertionError("Offer should return true for normal element");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should not be empty after offer");
        }
    }

    private static void testOfferCapacityFull() {
        int capacity = 1;
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(capacity);
        queue.offer("first");
        boolean result = queue.offer("second");
        if (result) {
            throw new AssertionError("Offer should return false when capacity is full");
        }
        if (queue.size() != capacity || !queue.contains("first")) {
            throw new AssertionError("Queue state incorrect after capacity full scenario");
        }
    }
}
