/*
 * @test
 * @summary Test Collections.removeIf delegates correctly and behaves as expected
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatchReturnsFalse();
        testRemoveIfEmptyCollectionReturnsFalse();
        testRemoveIfNullPredicateThrowsNPE();
    }

    private static void testRemoveIfRemovesMatchingElements() {
        List<String> list = new ArrayList<>();
        list.add("keep");
        list.add("remove");
        list.add("keep");
        list.add("remove");

        Predicate<String> predicate = new Predicate<String>() {
            public boolean test(String s) {
                return "remove".equals(s);
            }
        };

        boolean changed = new CollectionsWrapper<>(list).removeIf(predicate);

        if (!changed) {
            throw new AssertionError("Expected removeIf to return true when elements are removed");
        }
        if (list.size() != 2 || list.contains("remove")) {
            throw new AssertionError("Elements matching predicate were not removed correctly");
        }
    }

    private static void testRemoveIfNoMatchReturnsFalse() {
        List<String> list = new ArrayList<>();
        list.add("keep1");
        list.add("keep2");

        Predicate<String> predicate = new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        };

        boolean changed = new CollectionsWrapper<>(list).removeIf(predicate);

        if (changed) {
            throw new AssertionError("Expected removeIf to return false when no elements are removed");
        }
        if (list.size() != 2) {
            throw new AssertionError("List should remain unchanged when no elements match predicate");
        }
    }

    private static void testRemoveIfEmptyCollectionReturnsFalse() {
        List<String> list = new ArrayList<>();

        Predicate<String> predicate = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean changed = new CollectionsWrapper<>(list).removeIf(predicate);

        if (changed) {
            throw new AssertionError("Expected removeIf to return false on empty collection");
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List should remain empty after removeIf");
        }
    }

    private static void testRemoveIfNullPredicateThrowsNPE() {
        List<String> list = new ArrayList<>();
        list.add("element");

        try {
            new CollectionsWrapper<>(list).removeIf(null);
            throw new AssertionError("Expected NullPointerException when predicate is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Wrapper class to expose the removeIf method from Collections
    private static class CollectionsWrapper<E> {
        private final List<E> c;

        CollectionsWrapper(List<E> c) {
            this.c = c;
        }

        public boolean removeIf(Predicate<? super E> filter) {
            return c.removeIf(filter);
        }
    }
}
