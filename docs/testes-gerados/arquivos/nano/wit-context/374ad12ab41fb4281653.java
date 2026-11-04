/* @test    @summary Test PriorityQueue constructor with valid initial capacity    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    private static void testConstructorWithValidCapacity() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(10);
        if (pq == null) {
            throw new AssertionError("PriorityQueue instance should not be null for valid capacity");
        }
        if (pq.size() != 0) {
            throw new AssertionError("PriorityQueue should be empty after construction");
        }
    }

    private static void testConstructorWithZeroCapacity() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(0);
        if (pq == null) {
            throw new AssertionError("PriorityQueue instance should not be null for zero capacity");
        }
        if (pq.size() != 0) {
            throw new AssertionError("PriorityQueue should be empty after construction");
        }
    }

    private static void testConstructorWithNegativeCapacity() {
        boolean exceptionThrown = false;
        try {
            PriorityQueue<Integer> pq = new PriorityQueue<>(-1);
            // Should not reach here, expecting exception
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        }
    }
}
