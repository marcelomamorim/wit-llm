/*
 * @test
 * @summary Witup-derived tests for ArrayBlockingQueue.retainAll
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllRemovesNonRetainedElements();
        testRetainAllWithAllElementsRetainedReturnsFalse();
        testRetainAllOnEmptyQueueReturnsFalse();
        testRetainAllWithEmptyCollectionClearsQueue();
        testRetainAllNullThrowsNullPointerException();
        testRetainAllOnFairQueue();
        testCapacityPreservedAfterRetainAll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRetainAllRemovesNonRetainedElements() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        for (int i = 1; i <= 5; i++) {
            q.add(i);
        }
        List<Integer> keep = Arrays.asList(2, 4);
        boolean changed = q.retainAll(keep);
        check(changed, "retainAll should report a change");
        check(q.size() == 2, "size should be 2 after retainAll");
        check(q.contains(2), "2 should be retained");
        check(q.contains(4), "4 should be retained");
        check(!q.contains(1), "1 should be removed");
        check(!q.contains(3), "3 should be removed");
        check(!q.contains(5), "5 should be removed");
        Object[] arr = q.toArray();
        check(Arrays.equals(arr, new Object[] {2, 4}),
              "FIFO order of retained elements should be preserved");
    }

    static void testRetainAllWithAllElementsRetainedReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        boolean changed = q.retainAll(Arrays.asList(1, 2, 3, 4));
        check(!changed, "retainAll should return false when nothing is removed");
        check(q.size() == 3, "size should remain 3");
        Object[] arr = q.toArray();
        check(Arrays.equals(arr, new Object[] {1, 2, 3}), "contents should be unchanged");
    }

    static void testRetainAllOnEmptyQueueReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        boolean changed = q.retainAll(Arrays.asList(1, 2));
        check(!changed, "retainAll on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRetainAllWithEmptyCollectionClearsQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(7);
        q.add(8);
        boolean changed = q.retainAll(new ArrayList<Integer>());
        check(changed, "retainAll with empty collection should report a change");
        check(q.isEmpty(), "queue should be empty after retaining nothing");
        check(q.size() == 0, "size should be 0");
    }

    static void testRetainAllNullThrowsNullPointerException() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        boolean thrown = false;
        try {
            q.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) should throw NullPointerException");
        check(q.size() == 1, "queue should be unchanged after NPE");
        check(q.contains(1), "element should still be present after NPE");
    }

    static void testRetainAllOnFairQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4, true);
        q.add(10);
        q.add(20);
        q.add(30);
        boolean changed = q.retainAll(Arrays.asList(20, 30));
        check(changed, "fair queue retainAll should report a change");
        Object[] arr = q.toArray();
        check(Arrays.equals(arr, new Object[] {20, 30}),
              "fair queue should keep FIFO order of retained elements");
    }

    static void testCapacityPreservedAfterRetainAll() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        q.retainAll(Arrays.asList(3));
        check(q.size() == 1, "size should be 1");
        check(q.offer(4), "offer should succeed after retainAll frees space");
        check(q.offer(5), "offer should succeed up to capacity");
        check(!q.offer(6), "offer should fail when capacity 3 is reached");
        check(q.size() == 3, "size should equal capacity");
    }
}
