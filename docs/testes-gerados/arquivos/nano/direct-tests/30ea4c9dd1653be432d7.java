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
        testRemoveIfWithEmptyCollection();
        testRemoveIfWithNoMatchingElements();
        testRemoveIfWithAllMatchingElements();
        testRemoveIfWithSomeMatchingElements();
        testRemoveIfWithNullPredicate();
    }

    static void testRemoveIfWithEmptyCollection() {
        List<String> list = new ArrayList<>();
        boolean result = Collections.removeIf(list, s -> s.length() > 0);
        assert result == false : "Expected false for empty list";
        assert list.isEmpty() : "List should remain empty";
    }

    static void testRemoveIfWithNoMatchingElements() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean result = Collections.removeIf(list, n -> n > 10);
        assert result == false : "Expected false when no elements match";
        assert list.equals(Arrays.asList(1, 2, 3)) : "List should remain unchanged";
    }

    static void testRemoveIfWithAllMatchingElements() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "bb", "ccc"));
        boolean result = Collections.removeIf(list, s -> s.length() >= 1);
        assert result == true : "Expected true when all elements are removed";
        assert list.isEmpty() : "List should be empty after removal";
    }

    static void testRemoveIfWithSomeMatchingElements() {
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 10, 15, 20));
        boolean result = Collections.removeIf(list, n -> n % 10 == 0);
        assert result == true : "Expected true when some elements are removed";
        assert list.equals(Arrays.asList(5, 15, 20)) : "List should contain remaining elements";
    }

    static void testRemoveIfWithNullPredicate() {
        List<String> list = new ArrayList<>(Arrays.asList("x", "y"));
        try {
            Collections.removeIf(list, null);
            assert false : "Expected NullPointerException when predicate is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
