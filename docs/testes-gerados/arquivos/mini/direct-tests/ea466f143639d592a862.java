/*
 * @test
 * @summary Test java.util.PriorityQueue.offer(E) method behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testOfferNull();
        testOfferAndSize();
        testOfferMultipleElements();
    }

    private static void testOfferNull() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        try {
            pq.offer(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfferAndSize() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        boolean result = pq.offer("a");
        if (!result) {
            throw new Error("offer returned false unexpectedly");
        }
        if (pq.size() != 1) {
            throw new Error("Size after one offer should be 1 but was " + pq.size());
        }
        String head = pq.peek();
        if (!"a".equals(head)) {
            throw new Error("Head element should be 'a' but was " + head);
        }
    }

    private static void testOfferMultipleElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(5);
        pq.offer(1);
        pq.offer(3);
        if (pq.size() != 3) {
            throw new Error("Size after three offers should be 3 but was " + pq.size());
        }
        Integer head = pq.peek();
        if (head == null || head != 1) {
            throw new Error("Head element should be 1 but was " + head);
        }
    }
}
