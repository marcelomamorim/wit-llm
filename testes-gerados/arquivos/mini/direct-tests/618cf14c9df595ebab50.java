/*
 * @test
 * @summary Test PriorityBlockingQueue.retainAll(Collection<?>) method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllRemovesCorrectly();
        testRetainAllNoChange();
        testRetainAllEmptyQueue();
    }

    private static void testRetainAllWithNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean thrown = false;
        try {
            queue.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    private static void testRetainAllRemovesCorrectly() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> retain = Arrays.asList(2, 3, 4);
        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new AssertionError("retainAll should return true when elements are removed");
        }
        if (queue.contains(1)) {
            throw new AssertionError("Element 1 should have been removed");
        }
        if (!queue.contains(2) || !queue.contains(3)) {
            throw new AssertionError("Elements 2 and 3 should be retained");
        }
    }

    private static void testRetainAllNoChange() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(5);
        queue.add(6);
        Collection<Integer> retain = Arrays.asList(5, 6, 7);
        boolean changed = queue.retainAll(retain);
        if (changed) {
            throw new AssertionError("retainAll should return false when no elements are removed");
        }
        if (!queue.contains(5) || !queue.contains(6)) {
            throw new AssertionError("Elements 5 and 6 should remain in the queue");
        }
    }

    private static void testRetainAllEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Collection<Integer> retain = Arrays.asList(1, 2, 3);
        boolean changed = queue.retainAll(retain);
        if (changed) {
            throw new AssertionError("retainAll on empty queue should return false");
        }
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should remain empty after retainAll");
        }
    }
}
