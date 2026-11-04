/* @test    @summary Test Collections.removeIf method for deterministic behavior    @run main CollectionsWitupTest */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfReturnsFalseOnEmptyList();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoElementsMatch();
        testRemoveIfNullPredicate();
    }

    static void testRemoveIfReturnsFalseOnEmptyList() {
        List<String> list = new ArrayList<>();
        boolean result = Collections.removeIf(list, s -> s.startsWith("a"));
        assert result == false : "Expected false for empty list";
        assert list.isEmpty() : "List should remain empty";
    }

    static void testRemoveIfRemovesMatchingElements() {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("apricot");
        list.add("cherry");
        boolean result = Collections.removeIf(list, s -> s.startsWith("a"));
        assert result == true : "Expected true when elements are removed";
        assert list.size() == 2 : "List should contain 2 elements after removal";
        assert list.contains("banana") : "List should contain 'banana'";
        assert list.contains("cherry") : "List should contain 'cherry'";
    }

    static void testRemoveIfNoElementsMatch() {
        List<String> list = new ArrayList<>();
        list.add("dog");
        list.add("cat");
        boolean result = Collections.removeIf(list, s -> s.startsWith("z"));
        assert result == false : "Expected false when no elements are removed";
        assert list.size() == 2 : "List should remain unchanged";
        assert list.contains("dog") : "List should contain 'dog'";
        assert list.contains("cat") : "List should contain 'cat'";
    }

    static void testRemoveIfNullPredicate() {
        List<String> list = new ArrayList<>();
        list.add("test");
        try {
            Collections.removeIf(list, null);
            assert false : "Expected NullPointerException when predicate is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
