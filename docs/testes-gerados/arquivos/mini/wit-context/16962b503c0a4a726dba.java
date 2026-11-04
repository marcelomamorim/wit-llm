/*
 * @test
 * @summary Test PriorityQueue.add(E) for null element rejection and normal add
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testAddNullThrowsNPE();
        testAddNonNullSucceeds();
    }

    private static void testAddNullThrowsNPE() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        try {
            pq.add(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAddNonNullSucceeds() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        boolean added = pq.add("test");
        if (!added) {
            throw new RuntimeException("add returned false unexpectedly");
        }
        String head = pq.peek();
        if (!"test".equals(head)) {
            throw new RuntimeException("peek did not return the added element");
        }
    }
}
