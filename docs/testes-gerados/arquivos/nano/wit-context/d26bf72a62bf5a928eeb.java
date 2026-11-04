/*
 * @test
 * @summary Test for ConcurrentLinkedQueue.removeAll(Collection<?>) method
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;
import java.util.Arrays;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
    }

    static void testRemoveAllWithNullCollection() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        boolean exceptionThrown = false;
        try {
            queue.removeAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when calling removeAll with null");
        }
    }

    static void testRemoveAllWithEmptyCollection() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        boolean result = queue.removeAll(Arrays.asList());
        if (result != false) {
            throw new AssertionError("removeAll with empty collection should return false");
        }
        if (queue.size() != 3) {
            throw new AssertionError("Queue should remain unchanged after removeAll with empty collection");
        }
    }

    static void testRemoveAllWithNonEmptyCollection() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        boolean result = queue.removeAll(Arrays.asList(2, 4));
        if (result != true) {
            throw new AssertionError("removeAll with matching elements should return true");
        }
        if (queue.contains(2) || queue.contains(4)) {
            throw new AssertionError("Queue should not contain removed elements 2 and 4");
        }
        if (queue.size() != 3) {
            throw new AssertionError("Queue size should be 3 after removal");
        }
        // Verify remaining elements
        if (!queue.containsAll(Arrays.asList(1, 3, 5))) {
            throw new AssertionError("Remaining elements should be 1, 3, 5");
        }
    }
}
