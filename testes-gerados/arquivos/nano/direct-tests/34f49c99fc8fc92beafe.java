/*
 * @test
 * @summary Test Collections.removeIf with various predicates
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithEmptyList();
        testRemoveIfWithNoMatchingElements();
        testRemoveIfWithAllMatchingElements();
        testRemoveIfWithSomeMatchingElements();
        testRemoveIfWithNullPredicate();
    }

    static void testRemoveIfWithEmptyList() {
        List<String> list = new ArrayList<>();
        boolean result = list.removeIf(s -> s.length() > 0);
        assert result == false : "Expected false when removing from empty list";
        assert list.isEmpty() : "List should remain empty";
    }

    static void testRemoveIfWithNoMatchingElements() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean result = list.removeIf(n -> n > 10);
        assert result == false : "Expected false when no elements match";
        assert list.size() == 3 : "List should remain unchanged";
    }

    static void testRemoveIfWithAllMatchingElements() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "bb", "ccc"));
        boolean result = list.removeIf(s -> s.length() >= 1);
        assert result == true : "Expected true when all elements are removed";
        assert list.isEmpty() : "List should be empty after removal";
    }

    static void testRemoveIfWithSomeMatchingElements() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        boolean result = list.removeIf(n -> n % 2 == 0);
        assert result == true : "Expected true when some elements are removed";
        assert list.equals(Arrays.asList(1, 3, 5)) : "Remaining elements should be odd";
    }

    static void testRemoveIfWithNullPredicate() {
        List<String> list = new ArrayList<>(Arrays.asList("x", "y"));
        try {
            list.removeIf(null);
            assert false : "Expected NullPointerException when predicate is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
