/*
 * @test
 * @summary Test ConcurrentLinkedQueue.retainAll(Collection<?>) for null argument and basic behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllNullCollection();
        testRetainAllBasicBehavior();
    }

    private static void testRetainAllNullCollection() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
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
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Collection<String> retain = new ArrayList<>(Arrays.asList("b", "c", "d"));

        boolean changed = queue.retainAll(retain);

        // After retainAll, queue should contain only "b" and "c"
        if (!changed) {
            throw new AssertionError("retainAll should return true when elements are removed");
        }

        // Check contents
        if (queue.size() != 2) {
            throw new AssertionError("Queue size should be 2 after retainAll");
        }

        if (!queue.contains("b") || !queue.contains("c")) {
            throw new AssertionError("Queue should contain 'b' and 'c' after retainAll");
        }

        if (queue.contains("a")) {
            throw new AssertionError("Queue should not contain 'a' after retainAll");
        }

        // Calling retainAll with a collection that contains all elements should return false
        boolean changed2 = queue.retainAll(retain);
        if (changed2) {
            throw new AssertionError("retainAll should return false when no elements are removed");
        }
    }
}
