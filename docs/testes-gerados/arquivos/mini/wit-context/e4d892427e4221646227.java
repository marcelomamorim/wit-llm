/*
 * @test
 * @summary Test ArrayBlockingQueue.removeAll(Collection) for null argument and normal behavior
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
        testRemoveAllNullCollection();
        testRemoveAllNormalBehavior();
    }

    private static void testRemoveAllNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveAllNormalBehavior() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");

        List<String> toRemove = new ArrayList<>();
        toRemove.add("b");
        toRemove.add("x"); // element not in queue

        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("Expected removeAll to return true when elements removed");
        }

        if (queue.contains("b")) {
            throw new RuntimeException("Element 'b' should have been removed");
        }

        if (!queue.contains("a") || !queue.contains("c")) {
            throw new RuntimeException("Elements 'a' and 'c' should remain in the queue");
        }

        // Removing elements not present should return false
        List<String> notPresent = Arrays.asList("x", "y");
        boolean changed2 = queue.removeAll(notPresent);
        if (changed2) {
            throw new RuntimeException("Expected removeAll to return false when no elements removed");
        }
    }
}
