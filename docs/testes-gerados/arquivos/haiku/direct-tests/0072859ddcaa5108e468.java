/*
 * @test
 * @summary Behavioral tests for ArrayBlockingQueue.removeIf(Predicate)
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testRemovesMatchingElementsPreservingOrder();
        testNoMatchReturnsFalse();
        testEmptyQueueReturnsFalse();
        testRemoveAllMatching();
        testNullFilterThrowsNullPointerException();
        testRemainingCapacityAfterRemoval();
        testFairQueueRemoveIf();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemovesMatchingElementsPreservingOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        boolean changed = q.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when elements are removed");
        check(Arrays.toString(q.toArray()).equals("[1, 3, 5]"),
              "remaining elements must keep FIFO order, got " + Arrays.toString(q.toArray()));
    }

    static void testNoMatchReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.addAll(Arrays.asList(1, 3, 5));
        boolean changed = q.removeIf(x -> x % 2 == 0);
        check(!changed, "removeIf should return false when nothing matches");
        check(q.size() == 3, "size must be unchanged when nothing matches");
        check(Arrays.toString(q.toArray()).equals("[1, 3, 5]"), "contents must be unchanged");
    }

    static void testEmptyQueueReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        boolean changed = q.removeIf(x -> true);
        check(!changed, "removeIf on empty queue should return false");
        check(q.isEmpty(), "queue must remain empty");
    }

    static void testRemoveAllMatching() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.addAll(Arrays.asList(2, 4, 6));
        boolean changed = q.removeIf(x -> true);
        check(changed, "removeIf should return true when all elements are removed");
        check(q.isEmpty(), "queue must be empty after removing all elements");
        check(q.peek() == null, "peek must return null on emptied queue");
    }

    static void testNullFilterThrowsNullPointerException() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.addAll(Arrays.asList(1, 2, 3));
        Predicate<Integer> nullFilter = null;
        boolean thrown = false;
        try {
            q.removeIf(nullFilter);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(q.size() == 3, "queue must not be modified when filter is null");
        check(Arrays.toString(q.toArray()).equals("[1, 2, 3]"), "contents must be unchanged");
    }

    static void testRemainingCapacityAfterRemoval() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.addAll(Arrays.asList(1, 2, 3, 4, 5));
        check(q.remainingCapacity() == 0, "queue should be full before removal");
        q.removeIf(x -> x > 3);
        check(q.size() == 3, "size should be 3 after removing two elements");
        check(q.remainingCapacity() == 2, "remaining capacity should reflect removals");
        check(q.offer(9), "offer must succeed after removal frees space");
        check(Arrays.toString(q.toArray()).equals("[1, 2, 3, 9]"),
              "contents after offer, got " + Arrays.toString(q.toArray()));
    }

    static void testFairQueueRemoveIf() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5, true);
        q.addAll(Arrays.asList(10, 11, 12, 13));
        boolean changed = q.removeIf(x -> x >= 12);
        check(changed, "removeIf should return true on fair queue");
        check(Arrays.toString(q.toArray()).equals("[10, 11]"),
              "fair queue contents after removeIf, got " + Arrays.toString(q.toArray()));
    }
}
