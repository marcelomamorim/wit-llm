/*
 * @test
 * @summary Test ArrayBlockingQueue.removeAll(Collection) method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithPartialOverlap();
        testRemoveAllWithNoOverlap();
        testRemoveAllWithAllElements();
    }

    private static void testRemoveAllWithNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
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

    private static void testRemoveAllWithPartialOverlap() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        List<String> toRemove = Arrays.asList("b", "x");
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when elements are removed");
        }
        if (queue.size() != 2 || queue.contains("b")) {
            throw new RuntimeException("Element 'b' should be removed from the queue");
        }
    }

    private static void testRemoveAllWithNoOverlap() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        List<String> toRemove = Arrays.asList("x", "y");
        boolean changed = queue.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("removeAll should return false when no elements are removed");
        }
        if (queue.size() != 3) {
            throw new RuntimeException("Queue size should remain unchanged");
        }
    }

    private static void testRemoveAllWithAllElements() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        List<String> toRemove = Arrays.asList("a", "b", "c");
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when all elements are removed");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after removing all elements");
        }
    }
}
