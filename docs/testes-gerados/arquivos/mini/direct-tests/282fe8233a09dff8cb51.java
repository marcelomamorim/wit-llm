/*
 * @test
 * @summary Test LinkedBlockingQueue.retainAll(Collection<?>) behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithPartialRetain();
        testRetainAllWithFullRetain();
        testRetainAllWithNoRetain();
    }

    static void testRetainAllWithNullCollection() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetainAllWithEmptyCollection() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.put("a");
        queue.put("b");
        queue.put("c");

        boolean changed = queue.retainAll(Collections.emptyList());
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after retainAll with empty collection");
        }
    }

    static void testRetainAllWithPartialRetain() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.put("a");
        queue.put("b");
        queue.put("c");

        Collection<String> retain = Arrays.asList("a", "c");
        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        if (queue.size() != 2) {
            throw new RuntimeException("Queue size should be 2 after retainAll");
        }

        if (!queue.contains("a") || !queue.contains("c")) {
            throw new RuntimeException("Queue should contain only retained elements");
        }
    }

    static void testRetainAllWithFullRetain() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.put("a");
        queue.put("b");

        Collection<String> retain = Arrays.asList("a", "b");
        boolean changed = queue.retainAll(retain);
        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }

        if (queue.size() != 2) {
            throw new RuntimeException("Queue size should remain 2 after retainAll");
        }
    }

    static void testRetainAllWithNoRetain() throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.put("a");
        queue.put("b");

        Collection<String> retain = Arrays.asList("x", "y");
        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("retainAll should return true when all elements are removed");
        }

        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after retainAll with no matching elements");
        }
    }
}
