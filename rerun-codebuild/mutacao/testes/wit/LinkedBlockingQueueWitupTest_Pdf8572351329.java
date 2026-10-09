/*
 * @test
 * @summary Tests for java.util.concurrent.LinkedBlockingQueue.offer(E e)
 * @run main LinkedBlockingQueueWitupTest_Pdf8572351329
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueueWitupTest_Pdf8572351329 {

    public static void main(String[] args) {
        testOfferNullElement();
        testOfferNormalElement();
        testOfferWhenFullCapacity();
    }

    private static void testOfferNullElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
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
        boolean result1 = queue.offer("element1");
        boolean result2 = queue.offer("element2");
        if (!result1 || !result2) {
            throw new AssertionError("Offer should succeed for elements within capacity");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after two offers");
        }
    }

    private static void testOfferWhenFullCapacity() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        queue.offer("element1");
        boolean result = queue.offer("element2");
        if (result) {
            throw new AssertionError("Offer should fail when capacity is full");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should remain 1 after failed offer");
        }
    }
}
