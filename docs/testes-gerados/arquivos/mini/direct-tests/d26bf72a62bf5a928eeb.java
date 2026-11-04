/*
 * @test
 * @summary Test ConcurrentLinkedQueue.removeAll(Collection<?>) method
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithPartialMatch();
        testRemoveAllWithNoMatch();
        testRemoveAllWithAllMatch();
    }

    private static void testRemoveAllWithNullCollection() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        try {
            queue.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        boolean changed = queue.removeAll(new ArrayList<String>());
        if (changed) {
            throw new RuntimeException("removeAll with empty collection should not change the queue");
        }
        if (queue.size() != 3) {
            throw new RuntimeException("Queue size changed unexpectedly");
        }
    }

    private static void testRemoveAllWithPartialMatch() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        List<String> toRemove = Arrays.asList("b", "x");
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when elements are removed");
        }
        if (queue.contains("b")) {
            throw new RuntimeException("Element 'b' should have been removed");
        }
        if (!queue.contains("a") || !queue.contains("c")) {
            throw new RuntimeException("Elements 'a' and 'c' should remain in the queue");
        }
    }

    private static void testRemoveAllWithNoMatch() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        List<String> toRemove = Arrays.asList("x", "y");
        boolean changed = queue.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("removeAll should return false when no elements are removed");
        }
        if (queue.size() != 2) {
            throw new RuntimeException("Queue size should remain unchanged");
        }
    }

    private static void testRemoveAllWithAllMatch() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        List<String> toRemove = Arrays.asList("a", "b");
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when all elements are removed");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after removing all elements");
        }
    }
}
