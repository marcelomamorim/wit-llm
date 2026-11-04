/*
 * @test
 * @summary Deterministic tests for java.util.PriorityQueue.offer(E e) to check null rejection and normal insertion
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testOfferRejectsNull();
        testOfferInsertsElement();
    }

    private static void testOfferRejectsNull() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        try {
            pq.offer(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception, test passes
        }
    }

    private static void testOfferInsertsElement() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        boolean result = pq.offer("test");
        if (!result) {
            throw new Error("offer returned false unexpectedly");
        }
        String head = pq.peek();
        if (!"test".equals(head)) {
            throw new Error("peek did not return the inserted element");
        }
    }
}
