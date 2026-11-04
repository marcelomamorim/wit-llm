/*
 * @test
 * @summary Test java.util.ArrayList removeIf method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyListReturnsFalse();
        testRemoveIfWithNullPredicateThrowsNPE();
    }

    private static void testRemoveIfRemovesMatchingElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        Predicate<String> startsWithB = new Predicate<String>() {
            public boolean test(String s) {
                return s.startsWith("b");
            }
        };

        boolean removed = list.removeIf(startsWithB);

        if (!removed) {
            throw new RuntimeException("Expected removeIf to return true when elements are removed");
        }
        if (list.size() != 2 || list.contains("banana")) {
            throw new RuntimeException("Element starting with 'b' should be removed");
        }
    }

    private static void testRemoveIfReturnsFalseWhenNoMatch() {
        ArrayList<String> list = new ArrayList<>();
        list.add("apple");
        list.add("cherry");

        Predicate<String> startsWithZ = new Predicate<String>() {
            public boolean test(String s) {
                return s.startsWith("z");
            }
        };

        boolean removed = list.removeIf(startsWithZ);

        if (removed) {
            throw new RuntimeException("Expected removeIf to return false when no elements are removed");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List size should remain unchanged when no elements removed");
        }
    }

    private static void testRemoveIfOnEmptyListReturnsFalse() {
        ArrayList<String> list = new ArrayList<>();

        Predicate<String> any = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean removed = list.removeIf(any);

        if (removed) {
            throw new RuntimeException("Expected removeIf to return false on empty list");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("List should remain empty after removeIf");
        }
    }

    private static void testRemoveIfWithNullPredicateThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        list.add("apple");

        try {
            list.removeIf(null);
            throw new RuntimeException("Expected NullPointerException when predicate is null");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
