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
        testRemoveIfOnEmptyList();
    }

    static void testRemoveIfWithMatchingPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean result = Collections.removeIf(list, s -> s.equals("b"));
        assert result : "Expected removeIf to return true when element is removed";
        assert list.size() == 2 : "List size should be 2 after removal";
        assert list.contains("a") : "List should contain 'a'";
        assert list.contains("c") : "List should contain 'c'";
        assert !list.contains("b") : "List should not contain 'b'";
    }

    static void testRemoveIfWithNonMatchingPredicate() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean result = Collections.removeIf(list, s -> s.equals("d"));
        assert !result : "Expected removeIf to return false when no element is removed";
        assert list.size() == 3 : "List size should remain 3";
        assert list.contains("a") : "List should contain 'a'";
        assert list.contains("b") : "List should contain 'b'";
        assert list.contains("c") : "List should contain 'c'";
    }

    static void testRemoveIfOnEmptyList() {
        List<String> list = new ArrayList<>();
        boolean result = Collections.removeIf(list, s -> true);
        assert !result : "Expected removeIf to return false on empty list";
        assert list.isEmpty() : "List should remain empty";
    }
}
