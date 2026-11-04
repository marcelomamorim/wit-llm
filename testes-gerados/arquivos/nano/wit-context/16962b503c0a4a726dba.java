/* @test    @summary Test for PriorityQueue.add(E e) method to verify behavior with null input.    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testAddWithNull();
        testAddWithValidElement();
    }

    private static void testAddWithNull() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean exceptionThrown = false;
        try {
            pq.add(null);
        } catch (NullPointerException npe) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null to PriorityQueue");
        }
    }

    private static void testAddWithValidElement() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        boolean result = pq.add("element");
        if (!result) {
            throw new AssertionError("add() should return true when adding a valid element");
        }
        if (pq.isEmpty()) {
            throw new AssertionError("PriorityQueue should contain the added element");
        }
        String head = pq.peek();
        if (!"element".equals(head)) {
            throw new AssertionError("Head of PriorityQueue should be the added element");
        }
    }
}
