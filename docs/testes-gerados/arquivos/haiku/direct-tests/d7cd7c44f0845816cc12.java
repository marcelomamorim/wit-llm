/*
 * @test
 * @summary Behavioral tests for LinkedBlockingQueue.removeIf
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveIfNullFilterThrowsNPE();
        testRemoveIfRemovesMatchingAndPreservesOrder();
        testRemoveIfNoMatchReturnsFalse();
        testRemoveIfEmptyQueueReturnsFalse();
        testRemoveIfAllMatchEmptiesQueue();
        testRemoveIfUpdatesRemainingCapacity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveIfNullFilterThrowsNPE() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        try {
            q.removeIf(null);
            check(false, "expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must be unchanged after NPE");
        check(q.peek() == 1, "element must remain after NPE");
    }

    static void testRemoveIfRemovesMatchingAndPreservesOrder() throws InterruptedException {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2, 3, 4, 5));
        Predicate<Integer> even = x -> x % 2 == 0;
        check(q.removeIf(even), "removeIf should return true when elements are removed");
        check(q.size() == 3, "expected 3 elements left, got " + q.size());
        check(q.take() == 1, "expected 1 first");
        check(q.take() == 3, "expected 3 second");
        check(q.take() == 5, "expected 5 third");
        check(q.isEmpty(), "queue should be empty");
    }

    static void testRemoveIfNoMatchReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 3, 5));
        Predicate<Integer> even = x -> x % 2 == 0;
        check(!q.removeIf(even), "removeIf should return false when nothing matches");
        check(q.size() == 3, "queue size must be unchanged");
        check(q.peek() == 1, "head must be unchanged");
    }

    static void testRemoveIfEmptyQueueReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Predicate<Integer> any = x -> true;
        check(!q.removeIf(any), "removeIf on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRemoveIfAllMatchEmptiesQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(2, 4, 6));
        Predicate<Integer> even = x -> x % 2 == 0;
        check(q.removeIf(even), "removeIf should return true when all elements match");
        check(q.isEmpty(), "queue should be empty");
        check(q.size() == 0, "size should be 0");
    }

    static void testRemoveIfUpdatesRemainingCapacity() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        check(q.offer(1), "offer 1 should succeed");
        check(q.offer(2), "offer 2 should succeed");
        check(q.remainingCapacity() == 0, "capacity should be exhausted");
        Predicate<Integer> one = x -> x == 1;
        check(q.removeIf(one), "removeIf should remove 1");
        check(q.remainingCapacity() == 1, "remaining capacity should be 1");
        check(q.offer(3), "offer 3 should succeed after removal");
        check(!q.offer(4), "offer 4 should fail, queue is full");
    }
}
