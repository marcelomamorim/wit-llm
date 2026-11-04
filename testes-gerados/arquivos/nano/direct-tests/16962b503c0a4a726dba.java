/* @test    @summary Test PriorityQueue.add() method for basic functionality and null handling    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testAddWithValidElement();
        testAddWithNullElement();
    }

    private static void testAddWithValidElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean result = pq.add(10);
        if (!result) {
            throw new AssertionError("Expected add() to return true for valid element");
        }
        if (pq.isEmpty()) {
            throw new AssertionError("PriorityQueue should not be empty after add()");
        }
        if (pq.peek() != 10) {
            throw new AssertionError("Peek should return the added element 10");
        }
    }

    private static void testAddWithNullElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        try {
            pq.add(null);
            throw new AssertionError("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
