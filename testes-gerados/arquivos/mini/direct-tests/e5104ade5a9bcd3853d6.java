/*
 * @test
 * @summary Test PriorityBlockingQueue.removeAll(Collection<?>) method
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithSomeElements();
        testRemoveAllWithNoElementsRemoved();
    }

    private static void testRemoveAllWithNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean changed = queue.removeAll(new ArrayList<Integer>());
        if (changed) {
            throw new RuntimeException("removeAll with empty collection should not change the queue");
        }
        if (queue.size() != 3) {
            throw new RuntimeException("Queue size changed unexpectedly");
        }
    }

    private static void testRemoveAllWithSomeElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> toRemove = Arrays.asList(2, 3, 4);
        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should have removed elements");
        }
        if (queue.size() != 1 || !queue.contains(1)) {
            throw new RuntimeException("Queue contents incorrect after removeAll");
        }
    }

    private static void testRemoveAllWithNoElementsRemoved() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        Collection<Integer> toRemove = Arrays.asList(3, 4);
        boolean changed = queue.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("removeAll should not have removed any elements");
        }
        if (queue.size() != 2) {
            throw new RuntimeException("Queue size changed unexpectedly");
        }
    }
}
