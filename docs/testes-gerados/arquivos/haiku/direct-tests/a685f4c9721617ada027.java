/*
 * @test
 * @summary ConcurrentLinkedQueue.removeIf observable behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNpe();
        testEmptyQueueReturnsFalse();
        testNoMatchReturnsFalseAndKeepsElements();
        testRemoveSomeKeepsRemainingOrder();
        testRemoveAllMatching();
        testSecondCallAfterRemovalReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentLinkedQueue<Integer> queueOf(Integer... values) {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
        for (Integer v : values) {
            q.add(v);
        }
        return q;
    }

    static List<Integer> contents(ConcurrentLinkedQueue<Integer> q) {
        return new ArrayList<>(q);
    }

    static void testNullFilterThrowsNpe() {
        ConcurrentLinkedQueue<Integer> q = queueOf(1, 2);
        try {
            q.removeIf(null);
            throw new RuntimeException("expected NPE");
        } catch (NullPointerException expected) {
            // expected
        }
        check(contents(q).equals(java.util.Arrays.asList(1, 2)), "queue changed after NPE");
    }

    static void testEmptyQueueReturnsFalse() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
        Predicate<Integer> any = x -> true;
        check(!q.removeIf(any), "empty queue should return false");
        check(q.isEmpty(), "empty queue should remain empty");
    }

    static void testNoMatchReturnsFalseAndKeepsElements() {
        ConcurrentLinkedQueue<Integer> q = queueOf(1, 3);
        Predicate<Integer> even = x -> x % 2 == 0;
        check(!q.removeIf(even), "no match should return false");
        check(contents(q).equals(java.util.Arrays.asList(1, 3)), "elements changed without match");
    }

    static void testRemoveSomeKeepsRemainingOrder() {
        ConcurrentLinkedQueue<Integer> q = queueOf(1, 2, 3, 4, 5);
        Predicate<Integer> even = x -> x % 2 == 0;
        check(q.removeIf(even), "matching elements should return true");
        check(contents(q).equals(java.util.Arrays.asList(1, 3, 5)),
              "remaining elements or order incorrect: " + contents(q));
    }

    static void testRemoveAllMatching() {
        ConcurrentLinkedQueue<Integer> q = queueOf(2, 4);
        Predicate<Integer> even = x -> x % 2 == 0;
        check(q.removeIf(even), "all matching should return true");
        check(q.isEmpty(), "queue should be empty after removing all");
        check(q.peek() == null, "peek should be null after removing all");
    }

    static void testSecondCallAfterRemovalReturnsFalse() {
        ConcurrentLinkedQueue<Integer> q = queueOf(1, 2, 3, 5);
        Predicate<Integer> even = x -> x % 2 == 0;
        check(q.removeIf(even), "first call should remove 2");
        check(!q.removeIf(even), "second call should find nothing");
        check(contents(q).equals(java.util.Arrays.asList(1, 3, 5)), "unexpected contents");
    }
}
