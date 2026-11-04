/*
 * @test
 * @summary Test for PriorityQueue.offer(E e) method, including null input.
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithValidElement();
        testOfferWithNullElement();
    }

    private static void testOfferWithValidElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean result = pq.offer(10);
        if (!result) {
            throw new AssertionError("Expected offer to return true for valid element");
        }
        if (pq.isEmpty()) {
            throw new AssertionError("PriorityQueue should not be empty after offer");
        }
        if (pq.peek() != 10) {
            throw new AssertionError("PriorityQueue top element should be 10");
        }
    }

    private static void testOfferWithNullElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean exceptionThrown = false;
        try {
            pq.offer(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }
}
