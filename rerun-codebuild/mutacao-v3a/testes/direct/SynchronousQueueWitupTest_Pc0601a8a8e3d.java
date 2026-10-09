/* @test    @summary Test offer() method of SynchronousQueue for null and non-null elements    @run main SynchronousQueueWitupTest_Pc0601a8a8e3d */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest_Pc0601a8a8e3d {
    public static void main(String[] args) {
        testOfferNull();
        testOfferElement();
    }

    static void testOfferNull() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.offer(null);
            throw new AssertionError("Expected NullPointerException for offer(null)");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOfferElement() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        String element = "test";
        boolean result = queue.offer(element);
        if (result != true) {
            throw new AssertionError("offer should return true when element is successfully transferred");
        }
    }
}
