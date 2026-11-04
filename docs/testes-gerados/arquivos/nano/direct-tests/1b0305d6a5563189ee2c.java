/*
 * @test
 * @summary Test Collections.removeIf method for supported behavior
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithMatchingPredicate();
        testRemoveIfWithNonMatchingPredicate();
        testRemoveIfWithEmptyList();
        testRemoveIfWithNullPredicate();
    }

    static void testRemoveIfWithMatchingPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Predicate<String> predicate = s -> s.equals("b");
        boolean result = Collections.removeIf(list, predicate);
        assert result : "Expected removeIf to return true when elements are removed";
        assert list.size() == 2 : "List size should be 2 after removal";
        assert !list.contains("b") : "List should not contain 'b'";
    }

    static void testRemoveIfWithNonMatchingPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Predicate<String> predicate = s -> s.equals("d");
        boolean result = Collections.removeIf(list, predicate);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert list.size() == 3 : "List size should remain 3";
    }

    static void testRemoveIfWithEmptyList() {
        List<String> list = new ArrayList<>();
        Predicate<String> predicate = s -> true;
        boolean result = Collections.removeIf(list, predicate);
        assert !result : "Expected removeIf to return false on empty list";
        assert list.isEmpty() : "List should remain empty";
    }

    static void testRemoveIfWithNullPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        try {
            Collections.removeIf(list, null);
            assert false : "Expected NullPointerException when predicate is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
