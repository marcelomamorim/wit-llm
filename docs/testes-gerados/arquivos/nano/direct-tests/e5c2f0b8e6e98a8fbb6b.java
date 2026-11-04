/*
 * @test
 * @summary Test offer method of LinkedBlockingQueue for various inputs
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithNormalElement();
        testOfferWhenFullCapacity();
    }

    static void testOfferWithNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
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

    static void testOfferWithNormalElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        boolean result = queue.offer("element1");
        if (!result) {
            throw new AssertionError("Offer should succeed for non-null element");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after offer");
        }
    }

    static void testOfferWhenFullCapacity() {
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
