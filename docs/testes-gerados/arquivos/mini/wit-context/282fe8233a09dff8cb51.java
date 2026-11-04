/*
 * @test
 * @summary Test LinkedBlockingQueue.retainAll(Collection) for null argument and normal behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllNullArgument();
        testRetainAllNormalBehavior();
    }

    private static void testRetainAllNullArgument() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean npeThrown = false;
        try {
            queue.retainAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    private static void testRetainAllNormalBehavior() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        List<String> retain = Arrays.asList("b", "c", "d");

        boolean changed = queue.retainAll(retain);

        // After retainAll, queue should contain only "b" and "c"
        if (!changed) {
            throw new AssertionError("retainAll should return true when elements are removed");
        }

        // Check contents
        for (String s : queue) {
            if (!retain.contains(s)) {
                throw new AssertionError("Queue contains element not in retain collection: " + s);
            }
        }

        // Check that "a" was removed
        if (queue.contains("a")) {
            throw new AssertionError("Element 'a' should have been removed by retainAll");
        }
    }
}
