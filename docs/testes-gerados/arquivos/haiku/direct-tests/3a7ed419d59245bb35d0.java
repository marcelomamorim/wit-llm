/*
 * @test
 * @summary Tests PriorityQueue.removeIf: bulk removal, return value, heap order after removal, empty queue and null filter
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRemoveSomeReturnsTrue();
        testRemoveSomePreservesOrder();
        testNoMatchReturnsFalse();
        testEmptyQueueReturnsFalse();
        testRemoveAllEmptiesQueue();
        testWithComparator();
        testNullFilterThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveSomeReturnsTrue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= 10; i++) {
            pq.add(i);
        }
        boolean changed = pq.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when elements are removed");
        check(pq.size() == 5, "expected size 5 but was " + pq.size());
        check(!pq.contains(2), "2 should have been removed");
        check(pq.contains(3), "3 should remain");
    }

    static void testRemoveSomePreservesOrder() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int[] values = {9, 4, 7, 1, 8, 2, 6, 3, 5, 10};
        for (int v : values) {
            pq.add(v);
        }
        pq.removeIf(x -> x % 2 == 0);
        int[] expected = {1, 3, 5, 7, 9};
        for (int e : expected) {
            Integer polled = pq.poll();
            check(polled != null && polled == e,
                  "expected " + e + " but got " + polled);
        }
        check(pq.poll() == null, "queue should be empty after draining");
    }

    static void testNoMatchReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(3);
        pq.add(5);
        boolean changed = pq.removeIf(x -> x > 100);
        check(!changed, "removeIf should return false when nothing matches");
        check(pq.size() == 3, "size should remain 3 but was " + pq.size());
    }

    static void testEmptyQueueReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean changed = pq.removeIf(x -> true);
        check(!changed, "removeIf on empty queue should return false");
        check(pq.isEmpty(), "queue should remain empty");
    }

    static void testRemoveAllEmptiesQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < 20; i++) {
            pq.add(i);
        }
        boolean changed = pq.removeIf(x -> true);
        check(changed, "removeIf should return true when all elements are removed");
        check(pq.isEmpty(), "queue should be empty");
        check(pq.peek() == null, "peek should return null on empty queue");
    }

    static void testWithComparator() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 1; i <= 6; i++) {
            pq.add(i);
        }
        boolean changed = pq.removeIf(x -> x <= 3);
        check(changed, "removeIf should return true");
        int[] expected = {6, 5, 4};
        for (int e : expected) {
            Integer polled = pq.poll();
            check(polled != null && polled == e,
                  "expected " + e + " but got " + polled);
        }
        check(pq.isEmpty(), "queue should be empty");
    }

    static void testNullFilterThrowsNPE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        boolean thrown = false;
        try {
            pq.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) should throw NullPointerException");
        check(pq.size() == 1, "queue should be unchanged after null filter");
    }
}
