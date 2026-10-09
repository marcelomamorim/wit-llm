/* @test    @summary Test offer method of SynchronousQueue for null and non-null elements    @run main SynchronousQueueWitupTest_P84bd6fc8f774 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest_P84bd6fc8f774 {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithElement();
    }

    static void testOfferWithNull() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.offer(null);
        } catch (NullPointerException npe) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
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
