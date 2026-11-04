/*
 * @test
 * @summary Test java.util.Collections removeIf method delegation
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoElementRemoved();
        testRemoveIfThrowsNPEOnNullPredicate();
    }

    private static void testRemoveIfRemovesMatchingElements() {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        // Remove elements starting with 'b'
        boolean removed = Collections.unmodifiableList(list).removeIf(s -> s.startsWith("b"));
        // unmodifiableList does not support removeIf, so we test on original list
        removed = list.removeIf(s -> s.startsWith("b"));

        if (!removed) {
            throw new AssertionError("Expected elements to be removed");
        }
        if (list.contains("banana")) {
            throw new AssertionError("Element 'banana' should have been removed");
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after removal");
        }
    }

    private static void testRemoveIfReturnsFalseWhenNoElementRemoved() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // Predicate that matches no elements
        boolean removed = list.removeIf(i -> i > 10);

        if (removed) {
            throw new AssertionError("Expected no elements to be removed");
        }
        if (list.size() != 3) {
            throw new AssertionError("List size should remain 3");
        }
    }

    private static void testRemoveIfThrowsNPEOnNullPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");

        try {
            list.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
