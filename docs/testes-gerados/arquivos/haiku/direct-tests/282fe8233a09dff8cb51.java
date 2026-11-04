/*
 * @test
 * @summary Behavioral tests for LinkedBlockingQueue.retainAll
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrows();
        testRetainsOnlyContainedElements();
        testReturnsFalseWhenNothingRemoved();
        testEmptyCollectionClearsQueue();
        testPreservesFifoOrder();
        testBoundedQueueAfterRetainAll();
        testEmptyQueueReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrows() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        try {
            q.retainAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must be unchanged after NPE");
    }

    static void testRetainsOnlyContainedElements() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 5; i++) {
            q.add(i);
        }
        boolean changed = q.retainAll(Arrays.asList(2, 4, 6));
        check(changed, "retainAll should report modification");
        List<Integer> remaining = new ArrayList<>(q);
        check(remaining.equals(Arrays.asList(2, 4)),
              "unexpected contents: " + remaining);
    }

    static void testReturnsFalseWhenNothingRemoved() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "retainAll should return false when nothing removed");
        check(q.size() == 2, "size should remain 2");
    }

    static void testEmptyCollectionClearsQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        boolean changed = q.retainAll(Collections.<Integer>emptyList());
        check(changed, "retainAll with empty collection should modify queue");
        check(q.isEmpty(), "queue should be empty");
    }

    static void testPreservesFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(5);
        q.add(3);
        q.add(9);
        q.add(1);
        q.retainAll(Arrays.asList(9, 5, 1));
        check(q.poll().equals(5), "first element should be 5");
        check(q.poll().equals(9), "second element should be 9");
        check(q.poll().equals(1), "third element should be 1");
        check(q.poll() == null, "queue should be empty");
    }

    static void testBoundedQueueAfterRetainAll() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        q.retainAll(Arrays.asList(1, 3));
        check(q.remainingCapacity() == 1 + 1,
              "remaining capacity should be 2, was " + q.remainingCapacity());
        check(q.offer(4), "offer should succeed");
        check(q.offer(5), "offer should succeed");
        check(!q.offer(6), "offer should fail when bound reached");
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        boolean changed = q.retainAll(Arrays.asList(1));
        check(!changed, "empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }
}
