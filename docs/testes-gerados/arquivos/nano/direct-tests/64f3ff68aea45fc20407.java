/* @test    @summary Test removeIf method in Collections class    @run main CollectionsWitupTest */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithMatchingPredicate();
        testRemoveIfWithNoMatchingPredicate();
        testRemoveIfOnEmptyList();
    }

    private static void testRemoveIfWithMatchingPredicate() {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("apricot");
        list.add("cherry");
        Predicate<String> startsWithA = s -> s.startsWith("a");
        boolean result = Collections.removeIf(list, startsWithA);
        assert result : "Expected removeIf to return true when elements are removed";
        assert list.size() == 2 : "Expected list size 2 after removal";
        assert list.contains("banana") : "List should contain 'banana'";
        assert list.contains("cherry") : "List should contain 'cherry'";
    }

    private static void testRemoveIfWithNoMatchingPredicate() {
        List<String> list = new ArrayList<>();
        list.add("dog");
        list.add("cat");
        list.add("mouse");
        Predicate<String> startsWithZ = s -> s.startsWith("z");
        boolean result = Collections.removeIf(list, startsWithZ);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert list.size() == 3 : "List size should remain 3";
        assert list.contains("dog") : "List should contain 'dog'";
        assert list.contains("cat") : "List should contain 'cat'";
        assert list.contains("mouse") : "List should contain 'mouse'";
    }

    private static void testRemoveIfOnEmptyList() {
        List<String> list = new ArrayList<>();
        Predicate<String> anyPredicate = s -> true;
        boolean result = Collections.removeIf(list, anyPredicate);
        assert !result : "Expected removeIf to return false on empty list";
        assert list.isEmpty() : "List should remain empty";
    }
}
