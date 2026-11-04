/*
 * @test
 * @summary Test ArrayBlockingQueue.retainAll(Collection<?>) behavior including null argument and normal operation
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
        testRetainAllNullCollection();
        testRetainAllNormalBehavior();
    }

    private static void testRetainAllNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRetainAllNormalBehavior() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");

        // Retain only elements in the given collection
        List<String> retainList = Arrays.asList("b", "c", "d");
        boolean changed = queue.retainAll(retainList);

        // The queue should have removed "a", so changed should be true
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        // The queue should now contain only "b" and "c"
        List<String> expected = Arrays.asList("b", "c");
        if (!queue.containsAll(expected) || queue.size() != expected.size()) {
            throw new RuntimeException("Queue contents after retainAll do not match expected");
        }

        // Calling retainAll with a collection containing all elements should return false (no change)
        changed = queue.retainAll(expected);
        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }
    }
}
