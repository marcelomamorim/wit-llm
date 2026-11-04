/* @test    @summary Test removeIf method of ArrayList for various predicates    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithMatchingPredicate();
        testRemoveIfWithNoMatchingPredicate();
        testRemoveIfWithAllMatchingPredicate();
        testRemoveIfWithEmptyList();
    }

    static void testRemoveIfWithMatchingPredicate() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Predicate<Integer> isEven = n -> n % 2 == 0;
        boolean result = list.removeIf(isEven);
        assert result : "Expected removeIf to return true when elements are removed";
        assert list.size() == 2 : "Expected list size 2 after removal";
        assert list.contains(1) : "List should contain 1";
        assert list.contains(3) : "List should contain 3";
    }

    static void testRemoveIfWithNoMatchingPredicate() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Predicate<String> isDigit = s -> s.matches("\\d+");
        boolean result = list.removeIf(isDigit);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert list.size() == 3 : "List size should remain 3";
    }

    static void testRemoveIfWithAllMatchingPredicate() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("x");
        list.add("x");
        Predicate<String> alwaysTrue = s -> true;
        boolean result = list.removeIf(alwaysTrue);
        assert result : "Expected removeIf to return true when all elements are removed";
        assert list.isEmpty() : "List should be empty after removal";
    }

    static void testRemoveIfWithEmptyList() {
        ArrayList<Object> list = new ArrayList<>();
        Predicate<Object> anyPredicate = o -> true;
        boolean result = list.removeIf(anyPredicate);
        assert !result : "Expected removeIf to return false on empty list";
        assert list.isEmpty() : "List should remain empty";
    }
}
