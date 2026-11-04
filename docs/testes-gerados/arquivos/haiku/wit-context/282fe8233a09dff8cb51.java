/*
 * @test
 * @summary WITUP-derived checks for LinkedBlockingQueue.retainAll
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPEOnEmptyQueue();
        testNullArgumentThrowsNPEOnNonEmptyQueue();
        testRetainsOnlyElementsContainedInCollection();
        testReturnsFalseWhenNothingRemoved();
        testEmptyCollectionClearsQueue();
        testEmptyQueueReturnsFalse();
        testBoundedQueueRetainAll();
        testAllElementsRetainedKeepsOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPEOnEmptyQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        try {
            q.retainAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.isEmpty(), "queue must remain empty after failed call");
    }

    static void testNullArgumentThrowsNPEOnNonEmptyQueue() {
        LinkedBlockingQueue<Integer> q =
                new LinkedBlockingQueue<Integer>(Arrays.asList(1, 2, 3));
        try {
            q.retainAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 3, "queue must not be modified when argument is null");
        check(q.contains(1) && q.contains(2) && q.contains(3),
              "elements must be preserved when argument is null");
    }

    static void testRetainsOnlyElementsContainedInCollection() {
        LinkedBlockingQueue<Integer> q =
                new LinkedBlockingQueue<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = q.retainAll(Arrays.asList(2, 4, 6));
        check(changed, "retainAll must report a change");
        check(q.size() == 2, "expected 2 elements, got " + q.size());
        check(q.contains(2) && q.contains(4), "expected 2 and 4 to be retained");
        check(!q.contains(1) && !q.contains(3) && !q.contains(5),
              "non-matching elements must be removed");
    }

    static void testReturnsFalseWhenNothingRemoved() {
        LinkedBlockingQueue<Integer> q =
                new LinkedBlockingQueue<Integer>(Arrays.asList(1, 2));
        boolean changed = q.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "retainAll must return false when nothing is removed");
        check(q.size() == 2, "size must be unchanged");
    }

    static void testEmptyCollectionClearsQueue() {
        LinkedBlockingQueue<Integer> q =
                new LinkedBlockingQueue<Integer>(Arrays.asList(7, 8, 9));
        boolean changed = q.retainAll(Collections.<Integer>emptyList());
        check(changed, "retainAll with empty collection must report change");
        check(q.isEmpty(), "queue must be empty after retaining nothing");
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        boolean changed = q.retainAll(Arrays.asList(1, 2));
        check(!changed, "empty queue must return false");
        check(q.isEmpty(), "queue must remain empty");
    }

    static void testBoundedQueueRetainAll() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>(10);
        q.add(1);
        q.add(2);
        q.add(3);
        boolean changed = q.retainAll(Arrays.asList(3));
        check(changed, "bounded queue must report change");
        check(q.size() == 1 && q.peek() == 3, "only element 3 must remain");
        check(q.remainingCapacity() == 9, "remaining capacity must reflect removal");
    }

    static void testAllElementsRetainedKeepsOrder() {
        LinkedBlockingQueue<String> q =
                new LinkedBlockingQueue<String>(Arrays.asList("a", "b", "c"));
        boolean changed = q.retainAll(Arrays.asList("a", "b", "c", "d"));
        check(!changed, "no removal expected");
        Iterator<String> it = q.iterator();
        check(it.hasNext() && "a".equals(it.next()), "order must be preserved (a)");
        check(it.hasNext() && "b".equals(it.next()), "order must be preserved (b)");
        check(it.hasNext() && "c".equals(it.next()), "order must be preserved (c)");
        check(!it.hasNext(), "no extra elements expected");
    }
}
