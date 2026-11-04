/*
 * @test
 * @summary Witup-derived behavioral tests for PriorityBlockingQueue.removeAll
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testRemoveAllRemovesMatchingElements();
        testRemoveAllNoMatchReturnsFalse();
        testRemoveAllEmptyCollectionReturnsFalse();
        testRemoveAllOnEmptyQueueReturnsFalse();
        testRemoveAllWithAllElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static PriorityBlockingQueue<Integer> newQueueWithFive() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        q.add(2);
        q.add(4);
        return q;
    }

    static void testNullCollectionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = newQueueWithFive();
        try {
            q.removeAll(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            check(q.size() == 5, "Queue must be unchanged after NPE");
        }
    }

    static void testRemoveAllRemovesMatchingElements() {
        PriorityBlockingQueue<Integer> q = newQueueWithFive();
        boolean changed = q.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll should report modification");
        check(q.size() == 3, "Expected size 3 but was " + q.size());
        check(!q.contains(2), "2 should have been removed");
        check(!q.contains(4), "4 should have been removed");
        check(q.contains(1), "1 should remain");
        check(q.contains(3), "3 should remain");
        check(q.contains(5), "5 should remain");
    }

    static void testRemoveAllNoMatchReturnsFalse() {
        PriorityBlockingQueue<Integer> q = newQueueWithFive();
        boolean changed = q.removeAll(Arrays.asList(10, 20));
        check(!changed, "removeAll without matches should return false");
        check(q.size() == 5, "Size must remain 5");
    }

    static void testRemoveAllEmptyCollectionReturnsFalse() {
        PriorityBlockingQueue<Integer> q = newQueueWithFive();
        boolean changed = q.removeAll(Collections.<Integer>emptyList());
        check(!changed, "removeAll with empty collection should return false");
        check(q.size() == 5, "Size must remain 5");
    }

    static void testRemoveAllOnEmptyQueueReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean changed = q.removeAll(Arrays.asList(1, 2, 3));
        check(!changed, "removeAll on empty queue should return false");
        check(q.isEmpty(), "Queue must remain empty");
    }

    static void testRemoveAllWithAllElements() {
        PriorityBlockingQueue<Integer> q = newQueueWithFive();
        boolean changed = q.removeAll(Arrays.asList(1, 2, 3, 4, 5));
        check(changed, "removeAll with all elements should return true");
        check(q.isEmpty(), "Queue should be empty");
    }
}
