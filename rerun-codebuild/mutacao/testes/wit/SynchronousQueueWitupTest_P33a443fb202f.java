/*
 * @test
 * @summary Test for SynchronousQueue.offer method, including null input handling.
 * @run main SynchronousQueueWitupTest_P33a443fb202f
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest_P33a443fb202f {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithValidElement();
    }

    private static void testOfferWithNull() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
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

    private static void testOfferWithValidElement() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        String element = "test";
        boolean result = queue.offer(element);
        if (result != true) {
            throw new AssertionError("offer should return true when offering an element");
        }
    }
}
