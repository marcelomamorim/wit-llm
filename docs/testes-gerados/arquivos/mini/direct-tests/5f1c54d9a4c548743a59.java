/*
 * @test
 * @summary Test ArrayBlockingQueue.retainAll(Collection<?>) method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithPartialRetention();
        testRetainAllWithFullRetention();
        testRetainAllWithNoRetention();
    }

    static void testRetainAllWithNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetainAllWithEmptyCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");

        boolean changed = queue.retainAll(new ArrayList<String>());
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after retainAll with empty collection");
        }
    }

    static void testRetainAllWithPartialRetention() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");

        List<String> retain = Arrays.asList("b", "c", "d");
        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }
        if (queue.size() != 2 || !queue.contains("b") || !queue.contains("c")) {
            throw new RuntimeException("Queue should only contain elements in the retain collection");
        }
    }

    static void testRetainAllWithFullRetention() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");

        List<String> retain = Arrays.asList("a", "b");
        boolean changed = queue.retainAll(retain);
        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }
        if (queue.size() != 2 || !queue.contains("a") || !queue.contains("b")) {
            throw new RuntimeException("Queue contents should remain unchanged");
        }
    }

    static void testRetainAllWithNoRetention() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");

        List<String> retain = Arrays.asList("x", "y");
        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("retainAll should return true when all elements are removed");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after retainAll with no matching elements");
        }
    }
}
