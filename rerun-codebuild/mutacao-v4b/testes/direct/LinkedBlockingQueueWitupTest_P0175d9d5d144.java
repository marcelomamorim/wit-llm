/* @test
   @summary Verifies offer method behavior of LinkedBlockingQueue.
   @run main LinkedBlockingQueueWitupTest_P0175d9d5d144
 */
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Objects;
public class LinkedBlockingQueueWitupTest_P0175d9d5d144 {
    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithValidElement();
        testOfferWhenAtCapacity();
    }

    private static void testOfferWithNull() {
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

    private static void testOfferWithValidElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        boolean result = queue.offer("element");
        if (!result) {
            throw new AssertionError("Offer should succeed when queue is not at capacity");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should contain the offered element");
        }
    }

    private static void testOfferWhenAtCapacity() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        queue.offer("element1");
        boolean result = queue.offer("element2");
        if (result) {
            throw new AssertionError("Offer should fail when queue is at capacity");
        }
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should remain at capacity after failed offer");
        }
    }
}
