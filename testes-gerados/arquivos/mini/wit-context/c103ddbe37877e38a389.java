/*
 * @test
 * @summary Test LinkedBlockingDeque.retainAll(Collection) for null argument and basic behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testRetainAllNullArgument();
        testRetainAllBasicBehavior();
    }

    private static void testRetainAllNullArgument() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean npeThrown = false;
        try {
            deque.retainAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    private static void testRetainAllBasicBehavior() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        Collection<String> retain = new ArrayList<>();
        retain.add("b");
        retain.add("c");

        boolean changed = deque.retainAll(retain);
        if (!changed) {
            throw new AssertionError("Expected retainAll to return true when elements are removed");
        }

        // After retainAll, deque should contain only "b" and "c"
        if (deque.size() != 2) {
            throw new AssertionError("Expected size 2 after retainAll, got " + deque.size());
        }

        if (!deque.contains("b") || !deque.contains("c")) {
            throw new AssertionError("Expected deque to contain 'b' and 'c' after retainAll");
        }

        // Calling retainAll with a collection that contains all elements should return false
        changed = deque.retainAll(retain);
        if (changed) {
            throw new AssertionError("Expected retainAll to return false when no elements removed");
        }
    }
}
