/*
 * @test
 * @summary Witup-derived tests for PriorityBlockingQueue.retainAll
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testRetainsOnlyCommonElementsInPriorityOrder();
        testReturnsFalseWhenNothingRemoved();
        testEmptyCollectionClearsQueue();
        testRetainAllOnEmptyQueue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static PriorityBlockingQueue<Integer> newQueueWith(Integer... values) {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        for (Integer v : values) {
            q.add(v);
        }
        return q;
    }

    static void testNullArgumentThrowsNPE() {
        PriorityBlockingQueue<Integer> q = newQueueWith(1, 2, 3);
        boolean thrown = false;
        try {
            q.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) must throw NullPointerException");
        check(q.size() == 3, "queue must be unchanged after retainAll(null)");
    }

    static void testRetainsOnlyCommonElementsInPriorityOrder() {
        PriorityBlockingQueue<Integer> q = newQueueWith(5, 1, 3, 2, 4);
        boolean changed = q.retainAll(Arrays.asList(2, 4, 9));
        check(changed, "retainAll must return true when elements are removed");
        check(q.size() == 2, "expected size 2 but was " + q.size());
        check(q.contains(2) && q.contains(4), "common elements must be retained");
        check(!q.contains(1) && !q.contains(3) && !q.contains(5), "non-matching elements must be removed");
        check(q.poll().equals(2), "priority order must be preserved: first poll should be 2");
        check(q.poll().equals(4), "priority order must be preserved: second poll should be 4");
        check(q.isEmpty(), "queue should be empty after polling retained elements");
    }

    static void testReturnsFalseWhenNothingRemoved() {
        PriorityBlockingQueue<Integer> q = newQueueWith(1, 2, 3, 4, 5);
        boolean changed = q.retainAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        check(!changed, "retainAll must return false when no element is removed");
        check(q.size() == 5, "queue size must remain 5");
    }

    static void testEmptyCollectionClearsQueue() {
        PriorityBlockingQueue<Integer> q = newQueueWith(1, 2, 3);
        boolean changed = q.retainAll(Collections.emptyList());
        check(changed, "retainAll with empty collection must return true for non-empty queue");
        check(q.isEmpty(), "queue must be empty after retainAll with empty collection");
    }

    static void testRetainAllOnEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean changed = q.retainAll(Arrays.asList(1));
        check(!changed, "retainAll on empty queue must return false");
        check(q.isEmpty(), "queue must remain empty");
    }
}
