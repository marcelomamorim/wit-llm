/*
 * @test
 * @summary Verify PriorityQueue.offer rejects null elements and accepts non-null elements
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testOfferNullThrowsNullPointerException();
        testOfferNullLeavesQueueUnchanged();
        testOfferNonNullReturnsTrueAndIncrementsSize();
        testOfferNonNullHeadIsLeastElement();
    }

    static void testOfferNullThrowsNullPointerException() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        boolean thrown = false;
        try {
            pq.offer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("offer(null) did not throw NullPointerException");
        }
    }

    static void testOfferNullLeavesQueueUnchanged() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.offer("a");
        try {
            pq.offer(null);
        } catch (NullPointerException e) {
            // expected
        }
        if (pq.size() != 1) {
            throw new AssertionError("size changed after rejected null: " + pq.size());
        }
        if (!"a".equals(pq.peek())) {
            throw new AssertionError("head changed after rejected null: " + pq.peek());
        }
    }

    static void testOfferNonNullReturnsTrueAndIncrementsSize() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        if (!pq.offer(5)) {
            throw new AssertionError("offer returned false for non-null element");
        }
        if (pq.size() != 1) {
            throw new AssertionError("unexpected size: " + pq.size());
        }
    }

    static void testOfferNonNullHeadIsLeastElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(7);
        pq.offer(3);
        pq.offer(9);
        if (pq.peek() != 3) {
            throw new AssertionError("unexpected head: " + pq.peek());
        }
    }
}
