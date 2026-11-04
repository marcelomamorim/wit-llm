/*
 * @test
 * @summary Test deterministic behaviors of ArrayBlockingQueue.offer(E e)
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testOfferRejectsNull();
        testOfferReturnsFalseWhenFull();
        testOfferReturnsTrueWhenSpace();
    }

    private static void testOfferRejectsNull() {
        ArrayBlockingQueue<Object> q = new ArrayBlockingQueue<>(1);
        try {
            q.offer(null);
            throw new Error("Expected NullPointerException for null element");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfferReturnsFalseWhenFull() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        boolean firstOffer = q.offer("first");
        if (!firstOffer) {
            throw new Error("Expected first offer to succeed");
        }
        boolean secondOffer = q.offer("second");
        if (secondOffer) {
            throw new Error("Expected second offer to fail when queue is full");
        }
    }

    private static void testOfferReturnsTrueWhenSpace() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        boolean offer1 = q.offer(1);
        boolean offer2 = q.offer(2);
        if (!offer1 || !offer2) {
            throw new Error("Expected offers to succeed when queue has space");
        }
    }
}
