/*
 * @test
 * @summary Test PriorityQueue constructor with initial capacity
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        // Test with positive initial capacity
        PriorityQueue<Integer> pq = new PriorityQueue<>(10);
        if (pq.size() != 0) {
            throw new RuntimeException("Expected empty queue on construction");
        }

        // Test with zero initial capacity
        PriorityQueue<Integer> pqZero = new PriorityQueue<>(0);
        if (pqZero.size() != 0) {
            throw new RuntimeException("Expected empty queue on construction with zero capacity");
        }

        // Test with negative initial capacity - expect IllegalArgumentException
        try {
            new PriorityQueue<Integer>(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
