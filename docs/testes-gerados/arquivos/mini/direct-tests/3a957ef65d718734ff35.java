/*
 * @test
 * @summary Test LinkedTransferQueue.removeAll(Collection) method
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithSomeElements();
        testRemoveAllWithNoMatchingElements();
        testRemoveAllOnEmptyQueue();
    }

    static void testRemoveAllWithNullCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveAllWithEmptyCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        boolean changed = queue.removeAll(new ArrayList<String>());
        if (changed) {
            throw new RuntimeException("removeAll with empty collection should not change queue");
        }
        if (queue.size() != 2) {
            throw new RuntimeException("Queue size changed unexpectedly");
        }
    }

    static void testRemoveAllWithSomeElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Collection<String> toRemove = Arrays.asList("b", "c");
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should have removed elements");
        }
        if (queue.size() != 1) {
            throw new RuntimeException("Queue size after removeAll is incorrect");
        }
        if (!queue.contains("a")) {
            throw new RuntimeException("Remaining element 'a' missing after removeAll");
        }
    }

    static void testRemoveAllWithNoMatchingElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = queue.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("removeAll should not change queue when no elements match");
        }
        if (queue.size() != 2) {
            throw new RuntimeException("Queue size changed unexpectedly");
        }
    }

    static void testRemoveAllOnEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = queue.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("removeAll on empty queue should not change queue");
        }
        if (queue.size() != 0) {
            throw new RuntimeException("Empty queue size changed unexpectedly");
        }
    }
}
