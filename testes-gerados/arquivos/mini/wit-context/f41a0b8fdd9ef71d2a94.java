/*
 * @test
 * @summary Test LinkedTransferQueue.retainAll(Collection) for null argument and basic retain behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllNullArgument();
        testRetainAllBasicBehavior();
    }

    private static void testRetainAllNullArgument() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean npeThrown = false;
        try {
            queue.retainAll(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    private static void testRetainAllBasicBehavior() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Collection<String> retain = new ArrayList<>();
        retain.add("b");
        retain.add("c");

        boolean changed = queue.retainAll(retain);
        if (!changed) {
            throw new AssertionError("retainAll should return true when elements are removed");
        }

        // After retainAll, queue should contain only "b" and "c"
        Object[] remaining = queue.toArray();
        if (remaining.length != 2) {
            throw new AssertionError("Queue size after retainAll is incorrect, expected 2 but was " + remaining.length);
        }
        boolean containsB = false;
        boolean containsC = false;
        for (Object o : remaining) {
            if ("b".equals(o)) containsB = true;
            if ("c".equals(o)) containsC = true;
        }
        if (!containsB || !containsC) {
            throw new AssertionError("Queue contents after retainAll are incorrect, expected to contain 'b' and 'c'");
        }

        // Calling retainAll with a collection that contains all elements should return false (no change)
        changed = queue.retainAll(retain);
        if (changed) {
            throw new AssertionError("retainAll should return false when no elements are removed");
        }
    }
}
