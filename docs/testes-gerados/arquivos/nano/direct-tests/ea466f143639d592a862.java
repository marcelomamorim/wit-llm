/* @test    @summary Test PriorityQueue.offer with non-null element    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithValidElement();
        testOfferWithNullElement();
    }

    static void testOfferWithValidElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean result = pq.offer(10);
        if (!result) {
            throw new AssertionError("offer should return true for non-null element");
        }
        if (pq.peek() == null || pq.peek() != 10) {
            throw new AssertionError("PriorityQueue should contain the element 10 after offer");
        }
    }

    static void testOfferWithNullElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        try {
            pq.offer(null);
            throw new AssertionError("offer should throw NullPointerException for null element");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
