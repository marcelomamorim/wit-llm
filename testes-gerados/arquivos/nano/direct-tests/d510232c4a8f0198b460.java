/*
 * @test    @summary Test ArrayBlockingQueue.offer method for basic functionality and edge cases    @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithAvailableSpace();
        testOfferWhenFull();
        testOfferWithNullElement();
    }

    static void testOfferWithAvailableSpace() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        boolean result1 = queue.offer("element1");
        boolean result2 = queue.offer("element2");
        assert result1 : "Offer should succeed when space is available";
        assert result2 : "Offer should succeed when space is available";
        assert queue.size() == 2 : "Queue size should be 2 after two offers";
    }

    static void testOfferWhenFull() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        queue.offer("element1");
        boolean result = queue.offer("element2");
        assert !result : "Offer should fail when queue is full";
        assert queue.size() == 1 : "Queue size should remain 1 after failed offer";
    }

    static void testOfferWithNullElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        try {
            queue.offer(null);
            assert false : "offer(null) should throw NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
