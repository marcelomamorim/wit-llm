/*
 * @test
 * @summary Test LinkedBlockingQueue removeAll(Collection) method
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithPartialMatch();
        testRemoveAllWithFullMatch();
        testRemoveAllWithNoMatch();
    }

    private static void testRemoveAllWithNullCollection() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean thrown = false;
        try {
            queue.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException when passing null collection");
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        boolean changed = queue.removeAll(new ArrayList<String>());
        if (changed) {
            throw new AssertionError("removeAll with empty collection should not change the queue");
        }
        if (queue.size() != 3 || !queue.contains("a") || !queue.contains("b") || !queue.contains("c")) {
            throw new AssertionError("Queue contents changed unexpectedly after removeAll with empty collection");
        }
    }

    private static void testRemoveAllWithPartialMatch() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Collection<String> toRemove = Arrays.asList("b", "x");
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when elements are removed");
        }
        if (queue.size() != 2 || queue.contains("b") || !queue.contains("a") || !queue.contains("c")) {
            throw new AssertionError("Queue contents incorrect after removeAll with partial match");
        }
    }

    private static void testRemoveAllWithFullMatch() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Collection<String> toRemove = Arrays.asList("a", "b", "c");
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when all elements are removed");
        }
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after removeAll with full match");
        }
    }

    private static void testRemoveAllWithNoMatch() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = queue.removeAll(toRemove);
        if (changed) {
            throw new AssertionError("removeAll should return false when no elements are removed");
        }
        if (queue.size() != 2 || !queue.contains("a") || !queue.contains("b")) {
            throw new AssertionError("Queue contents changed unexpectedly after removeAll with no match");
        }
    }
}
