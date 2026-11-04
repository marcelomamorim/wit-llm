/*
 * @test
 * @summary Test LinkedTransferQueue.removeAll(Collection<?>) for null argument and normal behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullArgument();
        testRemoveAllNormalBehavior();
    }

    private static void testRemoveAllNullArgument() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean npeThrown = false;
        try {
            queue.removeAll(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when removeAll called with null");
        }
    }

    private static void testRemoveAllNormalBehavior() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Collection<String> toRemove = new ArrayList<>();
        toRemove.add("b");
        toRemove.add("x"); // element not in queue

        boolean changed = queue.removeAll(toRemove);

        if (!changed) {
            throw new AssertionError("Expected removeAll to return true when elements removed");
        }

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
            throw new AssertionError("Expected removeAll to return false when no elements removed");
        }
    }
}
