/* @test    @summary Test for SynchronousQueue.offer() method, covering null input case    @run main SynchronousQueueWitupTest_Pc0601a8a8e3d */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest_Pc0601a8a8e3d {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithValidElement();
    }

    private static void testOfferWithNull() {
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

    private static void testOfferWithValidElement() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        String element = "test";
        boolean result = queue.offer(element);
        if (!result) {
            throw new AssertionError("offer() should return true when a valid element is offered");
        }
    }
}
