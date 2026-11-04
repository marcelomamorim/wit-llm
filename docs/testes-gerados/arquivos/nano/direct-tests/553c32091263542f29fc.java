/* @test    @summary Test removeIf method of ArrayList for various predicates    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithAllMatchingPredicate();
        testRemoveIfWithNoMatchingPredicate();
        testRemoveIfWithPartialMatchingPredicate();
    }

    static void testRemoveIfWithNullPredicate() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.removeIf(null);
        } catch (NullPointerException e) {
            // Expected exception
        }
        assert list.size() == 3 : "List should remain unchanged when predicate is null";
    }

    static void testRemoveIfWithAllMatchingPredicate() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(6);
        boolean result = list.removeIf(e -> e % 2 == 0);
        assert result : "removeIf should return true when elements are removed";
        assert list.isEmpty() : "List should be empty after removing all matching elements";
    }

    static void testRemoveIfWithNoMatchingPredicate() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        boolean result = list.removeIf(e -> e % 2 == 0);
        assert !result : "removeIf should return false when no elements are removed";
        assert list.size() == 3 : "List should remain unchanged when no elements match";
    }

    static void testRemoveIfWithPartialMatchingPredicate() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        boolean result = list.removeIf(e -> e % 2 == 0);
        assert result : "removeIf should return true when elements are removed";
        assert list.size() == 2 : "List should contain only non-matching elements";
        assert list.contains(1) && list.contains(3) : "Remaining elements should be 1 and 3";
    }
}
