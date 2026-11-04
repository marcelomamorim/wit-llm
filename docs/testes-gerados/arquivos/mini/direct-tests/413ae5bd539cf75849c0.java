/*
 * @test
 * @summary Test CopyOnWriteArrayList.removeIf(Predicate) behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.Arrays;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfWithEmptyListReturnsFalse();
        testRemoveIfWithNoMatchingElementsReturnsFalse();
        testRemoveIfRejectsNullPredicate();
    }

    private static void testRemoveIfRemovesMatchingElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c", "d"});
        Predicate<String> filter = s -> s.equals("b") || s.equals("d");
        boolean changed = list.removeIf(filter);
        if (!changed) {
            throw new AssertionError("Expected removeIf to return true when elements are removed");
        }
        if (list.size() != 2 || !list.contains("a") || !list.contains("c")) {
            throw new AssertionError("List contents incorrect after removeIf");
        }
    }

    private static void testRemoveIfWithEmptyListReturnsFalse() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Predicate<String> filter = s -> true;
        boolean changed = list.removeIf(filter);
        if (changed) {
            throw new AssertionError("Expected removeIf to return false on empty list");
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List should remain empty after removeIf");
        }
    }

    private static void testRemoveIfWithNoMatchingElementsReturnsFalse() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"x", "y", "z"});
        Predicate<String> filter = s -> s.equals("a");
        boolean changed = list.removeIf(filter);
        if (changed) {
            throw new AssertionError("Expected removeIf to return false when no elements match");
        }
        if (list.size() != 3 || !list.containsAll(Arrays.asList("x", "y", "z"))) {
            throw new AssertionError("List contents changed unexpectedly after removeIf");
        }
    }

    private static void testRemoveIfRejectsNullPredicate() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.removeIf(null);
            throw new AssertionError("Expected NullPointerException when passing null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
