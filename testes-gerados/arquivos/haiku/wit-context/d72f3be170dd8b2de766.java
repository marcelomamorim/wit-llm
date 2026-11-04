/*
 * @test
 * @summary Witup-generated behavioral tests for LinkedBlockingQueue.removeAll
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testRemoveAllRemovesMatchingElements();
        testRemoveAllNoMatchReturnsFalse();
        testRemoveAllEmptyCollectionReturnsFalse();
        testRemoveAllUpdatesRemainingCapacity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        try {
            q.removeAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(q.size() == 1 && q.contains(1), "queue modified after NPE");
    }

    static void testRemoveAllRemovesMatchingElements() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        q.add(2);
        boolean changed = q.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll should report change");
        Object[] remaining = q.toArray();
        check(Arrays.equals(remaining, new Object[] {1, 3}),
              "unexpected remaining elements: " + Arrays.toString(remaining));
    }

    static void testRemoveAllNoMatchReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.removeAll(Arrays.asList(9));
        check(!changed, "removeAll should return false when nothing matches");
        check(q.size() == 2, "size changed when nothing matched");
    }

    static void testRemoveAllEmptyCollectionReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(7);
        boolean changed = q.removeAll(Collections.emptyList());
        check(!changed, "removeAll with empty collection should return false");
        check(q.size() == 1 && q.contains(7), "queue modified by empty removeAll");
    }

    static void testRemoveAllUpdatesRemainingCapacity() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(10);
        q.offer(5);
        q.offer(6);
        boolean changed = q.removeAll(Collections.singletonList(5));
        check(changed, "removeAll should report change");
        check(q.remainingCapacity() == 9, "unexpected remaining capacity: " + q.remainingCapacity());
        Object[] remaining = q.toArray();
        check(Arrays.equals(remaining, new Object[] {6}),
              "unexpected remaining elements: " + Arrays.toString(remaining));
    }
}
