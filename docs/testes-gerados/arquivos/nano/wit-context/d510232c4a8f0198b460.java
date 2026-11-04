/*
 * @test
 * @summary Teste do método offer de ArrayBlockingQueue para verificar comportamentos observáveis.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferNullElement();
        testOfferNormalElement();
        testOfferWhenFull();
    }

    static void testOfferNullElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
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

    static void testOfferNormalElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        boolean result1 = queue.offer("a");
        boolean result2 = queue.offer("b");
        if (!result1 || !result2) {
            throw new AssertionError("Offer should succeed for available capacity");
        }
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after two offers");
        }
    }

    static void testOfferWhenFull() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        queue.offer("a");
        boolean result = queue.offer("b");
        if (result) {
            throw new AssertionError("Offer should return false when queue is full");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should remain 1 when offer fails");
        }
    }
}
