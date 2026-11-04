/*
 * @test
 * @summary Witup-style tests for LinkedBlockingQueue.removeAll
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrows();
        testEmptyCollectionReturnsFalse();
        testRemovesMatchingElementsPreservingOrder();
        testNoMatchReturnsFalse();
        testRemoveAllElementsEmptiesQueue();
        testEmptyQueueReturnsFalse();
        testCapacityReleasedAfterRemoval();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static ArrayList<Integer> contents(LinkedBlockingQueue<Integer> q) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer i : q) {
            result.add(i);
        }
        return result;
    }

    static void testNullArgumentThrows() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        try {
            q.removeAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(contents(q).equals(Arrays.asList(1)), "queue changed after NPE");
    }

    static void testEmptyCollectionReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.removeAll(Collections.emptyList());
        check(!changed, "removeAll(empty) should return false");
        check(contents(q).equals(Arrays.asList(1, 2)), "queue changed by removeAll(empty)");
    }

    static void testRemovesMatchingElementsPreservingOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 6; i++) {
            q.add(i);
        }
        boolean changed = q.removeAll(Arrays.asList(2, 4, 6));
        check(changed, "removeAll should return true when elements are removed");
        check(contents(q).equals(Arrays.asList(1, 3, 5)),
              "unexpected contents after removeAll: " + contents(q));
    }

    static void testNoMatchReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.removeAll(Arrays.asList(7, 8));
        check(!changed, "removeAll with no matches should return false");
        check(contents(q).equals(Arrays.asList(1, 2)), "queue changed with no matches");
    }

    static void testRemoveAllElementsEmptiesQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        boolean changed = q.removeAll(Arrays.asList(1, 2, 3));
        check(changed, "removeAll of all elements should return true");
        check(q.isEmpty(), "queue should be empty");
        check(q.size() == 0, "size should be 0");
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        boolean changed = q.removeAll(Arrays.asList(1, 2));
        check(!changed, "removeAll on empty queue should return false");
        check(q.isEmpty(), "empty queue should remain empty");
    }

    static void testCapacityReleasedAfterRemoval() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        q.add(1);
        q.add(2);
        check(q.remainingCapacity() == 0, "queue should be full");
        boolean changed = q.removeAll(Arrays.asList(1));
        check(changed, "removeAll should return true");
        check(q.remainingCapacity() == 1, "remainingCapacity should be 1 after removal");
        check(q.offer(3), "offer should succeed after removal");
        check(!q.offer(4), "offer should fail when full again");
        check(contents(q).equals(Arrays.asList(2, 3)), "unexpected contents: " + contents(q));
    }
}
