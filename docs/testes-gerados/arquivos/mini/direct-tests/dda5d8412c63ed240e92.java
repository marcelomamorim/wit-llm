/*
 * @test
 * @summary Test java.util.ArrayDeque removeAll(Collection) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithPartialOverlap();
        testRemoveAllWithFullOverlap();
        testRemoveAllWithNoOverlap();
    }

    private static void testRemoveAllWithNullCollection() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        try {
            deque.removeAll(null);
            throw new AssertionError("Expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        boolean changed = deque.removeAll(Collections.emptyList());
        if (changed) {
            throw new AssertionError("removeAll with empty collection should not change deque");
        }
        if (deque.size() != 2 || !deque.contains("a") || !deque.contains("b")) {
            throw new AssertionError("Deque contents changed unexpectedly");
        }
    }

    private static void testRemoveAllWithPartialOverlap() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        Collection<String> toRemove = Arrays.asList("b", "x");
        boolean changed = deque.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when elements are removed");
        }
        if (deque.size() != 2 || deque.contains("b")) {
            throw new AssertionError("Element 'b' should have been removed");
        }
        if (!deque.contains("a") || !deque.contains("c")) {
            throw new AssertionError("Elements 'a' and 'c' should remain");
        }
    }

    private static void testRemoveAllWithFullOverlap() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = deque.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when all elements are removed");
        }
        if (!deque.isEmpty()) {
            throw new AssertionError("Deque should be empty after removing all elements");
        }
    }

    private static void testRemoveAllWithNoOverlap() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = deque.removeAll(toRemove);
        if (changed) {
            throw new AssertionError("removeAll should return false when no elements are removed");
        }
        if (deque.size() != 2 || !deque.contains("a") || !deque.contains("b")) {
            throw new AssertionError("Deque contents changed unexpectedly");
        }
    }
}
