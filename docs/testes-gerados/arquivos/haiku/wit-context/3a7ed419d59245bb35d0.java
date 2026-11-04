/*
 * @test
 * @summary Witup-generated behavioral checks for PriorityQueue.removeIf
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfWithoutMatchReturnsFalse();
        testRemoveIfOnEmptyQueueReturnsFalse();
    }

    static void testNullFilterThrowsNPE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        boolean thrown = false;
        try {
            pq.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null filter");
        }
        if (pq.size() != 2) {
            throw new RuntimeException("Queue changed after null filter: size=" + pq.size());
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= 6; i++) {
            pq.add(i);
        }
        boolean changed = pq.removeIf(x -> x % 2 == 0);
        if (!changed) {
            throw new RuntimeException("removeIf should return true when elements are removed");
        }
        if (pq.size() != 3) {
            throw new RuntimeException("Unexpected size: " + pq.size());
        }
        if (pq.contains(2) || pq.contains(4) || pq.contains(6)) {
            throw new RuntimeException("Even elements were not removed");
        }
        int[] expected = {1, 3, 5};
        for (int e : expected) {
            Integer polled = pq.poll();
            if (polled == null || polled != e) {
                throw new RuntimeException("Heap order broken after removeIf: expected " + e + " got " + polled);
            }
        }
        if (!pq.isEmpty()) {
            throw new RuntimeException("Queue should be empty");
        }
    }

    static void testRemoveIfWithoutMatchReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(3);
        pq.add(5);
        boolean changed = pq.removeIf(x -> x > 100);
        if (changed) {
            throw new RuntimeException("removeIf should return false when nothing matches");
        }
        if (pq.size() != 3) {
            throw new RuntimeException("Size changed without matches: " + pq.size());
        }
    }

    static void testRemoveIfOnEmptyQueueReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean changed = pq.removeIf(x -> true);
        if (changed) {
            throw new RuntimeException("removeIf on empty queue should return false");
        }
        if (!pq.isEmpty()) {
            throw new RuntimeException("Empty queue should remain empty");
        }
    }
}
