/*
 * @test
 * @summary Tests for java.util.concurrent.LinkedBlockingQueue.offer(E e)
 * @run main LinkedBlockingQueueWitupTest_Pa106fe95f17b
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueWitupTest_Pa106fe95f17b {

    public static void main(String[] args) {
        testOfferNullElement();
        testOfferNormalElement();
        testOfferWhenFullCapacity();
    }

    private static void testOfferNullElement() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
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
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        boolean result = queue.offer("element");
        if (!result) {
            throw new AssertionError("Offer should succeed for non-full queue");
        }
        if (queue.size() != 1 || !queue.contains("element")) {
            throw new AssertionError("Queue state invalid after offer");
        }
    }

    private static void testOfferWhenFullCapacity() {
        final int capacity = 1;
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(capacity);
        queue.offer("first");
        boolean result = queue.offer("second");
        if (result) {
            throw new AssertionError("Offer should fail when capacity is full");
        }
        if (queue.size() != capacity || queue.contains("second")) {
            throw new AssertionError("Queue should not contain the second element");
        }
    }
}
