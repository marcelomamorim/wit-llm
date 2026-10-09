/* @test    @summary Test for SynchronousQueue.offer method handling null input    @run main SynchronousQueueWitupTest_P84bd6fc8f774 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest_P84bd6fc8f774 {

    public static void main(String[] args) {
        testOfferNullInput();
        testOfferNormalInput();
    }

    private static void testOfferNullInput() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        boolean threwException = false;
        try {
            queue.offer(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }

    private static void testOfferNormalInput() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        String element = "test";
        boolean result = queue.offer(element);
        if (result != true) {
            throw new AssertionError("offer should return true when offering an element");
        }
    }
}
