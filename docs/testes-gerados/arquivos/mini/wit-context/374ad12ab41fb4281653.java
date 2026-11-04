/*
 * @test
 * @summary Test PriorityQueue(int initialCapacity) constructor for valid and invalid capacities
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testInvalidInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Test with a valid initial capacity (1)
        PriorityQueue<Integer> pq = new PriorityQueue<>(1);
        if (pq == null) {
            throw new AssertionError("PriorityQueue constructor returned null for capacity 1");
        }
        // Test with a larger valid initial capacity
        pq = new PriorityQueue<>(10);
        if (pq == null) {
            throw new AssertionError("PriorityQueue constructor returned null for capacity 10");
        }
    }

    private static void testInvalidInitialCapacity() {
        // According to WIT expath and source, initialCapacity < 1 throws IllegalArgumentException
        try {
            new PriorityQueue<>(0);
            throw new AssertionError("Expected IllegalArgumentException for initialCapacity=0");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new PriorityQueue<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for initialCapacity=-1");
        } catch (IllegalArgumentException expected) {
        }
    }
}
