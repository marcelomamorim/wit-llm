/*
 * @test
 * @summary Tests for ArrayList.removeIf(Predicate)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithEmptyList();
        testRemoveIfWithMatchingPredicate();
        testRemoveIfWithNonMatchingPredicate();
    }

    private static void testRemoveIfWithNullPredicate() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.removeIf(null);
            // Expect NullPointerException
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    private static void testRemoveIfWithEmptyList() {
        ArrayList<Integer> list = new ArrayList<>();
        boolean result = list.removeIf(e -> e > 0);
        if (result != false) {
            throw new AssertionError("removeIf on empty list should return false");
        }
        if (list.size() != 0) {
            throw new AssertionError("List should remain empty after removeIf");
        }
    }

    private static void testRemoveIfWithMatchingPredicate() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        boolean result = list.removeIf(e -> e % 2 == 0); // remove even numbers
        if (result != true) {
            throw new AssertionError("Expected true when elements are removed");
        }
        if (list.size() != 2 || list.contains(2)) {
            throw new AssertionError("List should contain only odd numbers after removal");
        }
    }

    private static void testRemoveIfWithNonMatchingPredicate() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        boolean result = list.removeIf(s -> s.equals("z")); // no element matches
        if (result != false) {
            throw new AssertionError("Expected false when no elements are removed");
        }
        if (list.size() != 2) {
            throw new AssertionError("List should remain unchanged when predicate matches no elements");
        }
    }
}
