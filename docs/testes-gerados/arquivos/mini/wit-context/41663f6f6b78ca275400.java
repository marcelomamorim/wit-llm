/*
 * @test
 * @summary Test ConcurrentLinkedDeque.retainAll(Collection<?>) for NPE and basic behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRetainAllNullCollection();
        testRetainAllBasicBehavior();
    }

    private static void testRetainAllNullCollection() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean npeThrown = false;
        try {
            deque.retainAll(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    private static void testRetainAllBasicBehavior() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        Collection<String> retain = new ArrayList<>();
        retain.add("b");
        retain.add("c");

        boolean changed = deque.retainAll(retain);
        if (!changed) {
            throw new AssertionError("retainAll should return true when elements are removed");
        }

        // After retainAll, deque should contain only "b" and "c"
        if (deque.size() != 2 || !deque.contains("b") || !deque.contains("c")) {
            throw new AssertionError("Deque contents incorrect after retainAll");
        }

        // Calling retainAll with a collection containing all elements should return false (no change)
        changed = deque.retainAll(retain);
        if (changed) {
            throw new AssertionError("retainAll should return false when no elements are removed");
        }

        // Calling retainAll with empty collection should remove all elements
        changed = deque.retainAll(new ArrayList<String>());
        if (!changed) {
            throw new AssertionError("retainAll should return true when all elements are removed");
        }
        if (!deque.isEmpty()) {
            throw new AssertionError("Deque should be empty after retainAll with empty collection");
        }
    }
}
