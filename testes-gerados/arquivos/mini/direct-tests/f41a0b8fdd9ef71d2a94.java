/*
 * @test
 * @summary Test LinkedTransferQueue.retainAll(Collection<?>) method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithPartialRetain();
        testRetainAllWithFullRetain();
        testRetainAllWithNoRetain();
    }

    static void testRetainAllWithNullCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetainAllWithEmptyCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        boolean changed = queue.retainAll(Collections.emptyList());
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue should be empty after retainAll with empty collection");
        }
    }

    static void testRetainAllWithPartialRetain() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Collection<String> retain = Arrays.asList("b", "c", "d");
        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }
        if (queue.size() != 2 || !queue.contains("b") || !queue.contains("c")) {
            throw new RuntimeException("Queue should contain only elements in retain collection");
        }
    }

    static void testRetainAllWithFullRetain() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        Collection<String> retain = Arrays.asList("a", "b");
        boolean changed = queue.retainAll(retain);
        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }
        if (queue.size() != 2 || !queue.contains("a") || !queue.contains("b")) {
            throw new RuntimeException("Queue should remain unchanged");
        }
    }

    static void testRetainAllWithNoRetain() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
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
