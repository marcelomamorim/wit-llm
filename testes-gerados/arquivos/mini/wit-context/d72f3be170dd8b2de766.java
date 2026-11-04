/*
 * @test
 * @summary Deterministic tests for LinkedBlockingQueue.removeAll(Collection<?>)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullCollection();
        testRemoveAllWithNonNullCollection();
    }

    private static void testRemoveAllNullCollection() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveAllWithNonNullCollection() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Collection<String> toRemove = new ArrayList<>();
        toRemove.add("b");
        toRemove.add("x"); // element not in queue

        boolean result = queue.removeAll(toRemove);

        // The method returns true if any element was removed
        if (!result) {
            throw new RuntimeException("Expected removeAll to return true when elements removed");
        }

        // Check that "b" was removed and "a" and "c" remain
        if (queue.contains("b")) {
            throw new RuntimeException("Element 'b' was not removed");
        }
        if (!queue.contains("a") || !queue.contains("c")) {
            throw new RuntimeException("Elements 'a' or 'c' missing unexpectedly");
        }

        // Remove elements not in queue should return false
        Collection<String> notPresent = Arrays.asList("x", "y");
        boolean result2 = queue.removeAll(notPresent);
        if (result2) {
            throw new RuntimeException("Expected removeAll to return false when no elements removed");
        }
    }
}
