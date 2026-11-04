/*
 * @test
 * @summary Tests for ArrayBlockingQueue retainAll method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithNonNullCollection();
    }

    static void testRetainAllWithNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.addAll(Arrays.asList("a", "b", "c"));
        boolean exceptionThrown = false;
        try {
            queue.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when retainAll called with null");
        }
    }

    static void testRetainAllWithNonNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.addAll(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList("a", "b");
        boolean result = queue.retainAll(c);
        if (!result) {
            throw new AssertionError("retainAll should return true when elements are removed");
        }
        if (queue.size() != 2 || !queue.contains("a") || !queue.contains("b")) {
            throw new AssertionError("Queue contents incorrect after retainAll");
        }
        // Test retainAll with collection that does not change queue
        result = queue.retainAll(Arrays.asList("a", "b"));
        if (result) {
            throw new AssertionError("retainAll should return false when no change");
        }
    }
}
