/*
 * @test
 * @summary Test ConcurrentLinkedQueue.removeAll(Collection<?>) for null argument and basic removal
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullArgument();
        testRemoveAllBasicFunctionality();
    }

    private static void testRemoveAllNullArgument() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        boolean npeThrown = false;
        try {
            queue.removeAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to removeAll");
        }
    }

    private static void testRemoveAllBasicFunctionality() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Collection<String> toRemove = new ArrayList<>();
        toRemove.add("b");
        toRemove.add("x"); // element not in queue

        boolean changed = queue.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when elements are removed");
        }

        // After removal, queue should contain "a" and "c" only
        if (queue.contains("b")) {
            throw new AssertionError("Element 'b' should have been removed");
        }
        if (!queue.contains("a") || !queue.contains("c")) {
            throw new AssertionError("Elements 'a' and 'c' should remain in the queue");
        }

        // Removing elements not present should return false
        Collection<String> notPresent = Arrays.asList("x", "y");
        boolean changed2 = queue.removeAll(notPresent);
        if (changed2) {
            throw new AssertionError("removeAll should return false when no elements are removed");
        }
    }
}
