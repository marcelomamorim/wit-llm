/* @test    @summary Test offer method of SynchronousQueue for null and non-null elements    @run main SynchronousQueueWitupTest */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithElement();
    }

    static void testOfferWithNull() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        boolean threw = false;
        try {
            queue.offer(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }

    static void testOfferWithElement() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        String element = "test";
        boolean result = queue.offer(element);
        if (result != true) {
            throw new AssertionError("offer should return true when element is offered");
        }
    }
}
