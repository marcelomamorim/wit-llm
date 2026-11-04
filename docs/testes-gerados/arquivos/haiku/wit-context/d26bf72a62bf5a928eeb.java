/*
 * @test
 * @summary Witup-generated checks for ConcurrentLinkedQueue.removeAll(Collection)
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeAll(null) must throw NullPointerException");
        check(q.size() == 1 && q.contains(1), "queue must be unchanged after NPE");
    }

    static void testRemovesContainedElements() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 3, 4));
        boolean changed = q.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll must return true when elements are removed");
        check(q.size() == 2, "expected size 2 but was " + q.size());
        check(q.contains(1) && q.contains(3), "remaining elements must be 1 and 3");
        check(!q.contains(2) && !q.contains(4), "removed elements must be absent");
    }

    static void testDisjointCollectionReturnsFalse() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 3));
        boolean changed = q.removeAll(Arrays.asList(7, 8));
        check(!changed, "removeAll with disjoint collection must return false");
        check(q.size() == 3, "queue size must remain 3");
        check(q.contains(1) && q.contains(2) && q.contains(3), "queue contents must be unchanged");
    }

    static void testEmptyCollectionReturnsFalse() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(5, 6));
        boolean changed = q.removeAll(Collections.emptyList());
        check(!changed, "removeAll with empty collection must return false");
        check(q.size() == 2, "queue size must remain 2");
    }

    static void testEmptyQueueReturnsFalse() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
        List<Integer> c = new ArrayList<>();
        c.add(1);
        boolean changed = q.removeAll(c);
        check(!changed, "removeAll on empty queue must return false");
        check(q.isEmpty(), "queue must remain empty");
    }

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testRemovesContainedElements();
        testDisjointCollectionReturnsFalse();
        testEmptyCollectionReturnsFalse();
        testEmptyQueueReturnsFalse();
    }
}
