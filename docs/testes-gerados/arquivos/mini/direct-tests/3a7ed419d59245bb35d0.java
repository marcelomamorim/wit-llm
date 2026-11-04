/*
 * @test
 * @summary Test PriorityQueue.removeIf(Predicate) behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.Objects;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatchReturnsFalse();
    }

    private static void testRemoveIfWithNullPredicate() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        try {
            pq.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfRemovesMatchingElements() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("apple");
        pq.add("banana");
        pq.add("cherry");

        Predicate<String> startsWithB = new Predicate<String>() {
            public boolean test(String s) {
                return s.startsWith("b");
            }
        };

        boolean removed = pq.removeIf(startsWithB);
        if (!removed) {
            throw new RuntimeException("Expected elements to be removed");
        }
        if (pq.contains("banana")) {
            throw new RuntimeException("Element 'banana' should have been removed");
        }
        if (!pq.contains("apple") || !pq.contains("cherry")) {
            throw new RuntimeException("Non-matching elements should remain");
        }
    }

    private static void testRemoveIfNoMatchReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);

        Predicate<Integer> greaterThanTen = new Predicate<Integer>() {
            public boolean test(Integer i) {
                return i > 10;
            }
        };

        boolean removed = pq.removeIf(greaterThanTen);
        if (removed) {
            throw new RuntimeException("Expected no elements to be removed");
        }
        if (pq.size() != 3) {
            throw new RuntimeException("Queue size should remain unchanged");
        }
    }
}
