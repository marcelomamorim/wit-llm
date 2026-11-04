/*
 * @test
 * @summary Test ArrayDeque.removeAll(Collection) for null argument and normal behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullCollection();
        testRemoveAllNormalBehavior();
    }

    private static void testRemoveAllNullCollection() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        try {
            deque.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveAllNormalBehavior() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        Collection<String> toRemove = Arrays.asList("b", "c");
        boolean changed = deque.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when elements removed");
        }
        if (deque.size() != 1 || !deque.contains("a")) {
            throw new RuntimeException("removeAll did not remove the correct elements");
        }

        // Removing elements not present should return false
        Collection<String> notPresent = Arrays.asList("x", "y");
        boolean changed2 = deque.removeAll(notPresent);
        if (changed2) {
            throw new RuntimeException("removeAll should return false when no elements removed");
        }

        // Test that concurrent modification exception is not thrown in normal single-threaded usage
        // The WIT expath suggests possible CME under some internal conditions, but normal usage should not throw
        try {
            deque.removeAll(Arrays.asList("a"));
        } catch (ConcurrentModificationException cme) {
            throw new RuntimeException("Unexpected ConcurrentModificationException in normal removeAll usage", cme);
        }
    }
}
