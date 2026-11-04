/*
 * @test
 * @summary Test PriorityBlockingQueue.removeAll(Collection<?>) for null and normal cases
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullCollection();
        testRemoveAllNormalBehavior();
    }

    private static void testRemoveAllNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        try {
            queue.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveAllNormalBehavior() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        List<Integer> toRemove = Arrays.asList(2, 3, 4); // 4 not in queue
        boolean changed = queue.removeAll(toRemove);

        if (!changed) {
            throw new RuntimeException("Expected removeAll to return true when elements removed");
        }

        if (queue.contains(2) || queue.contains(3)) {
            throw new RuntimeException("Elements 2 and 3 should have been removed");
        }

        if (!queue.contains(1)) {
            throw new RuntimeException("Element 1 should remain in the queue");
        }

        // Removing elements not present should return false
        changed = queue.removeAll(Arrays.asList(5, 6));
        if (changed) {
            throw new RuntimeException("Expected removeAll to return false when no elements removed");
        }
    }
}
