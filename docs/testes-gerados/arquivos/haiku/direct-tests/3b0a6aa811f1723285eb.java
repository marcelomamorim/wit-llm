/*
 * @test
 * @summary Witup-style tests for ArrayList.removeAll behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemovesMatchingElementsAndKeepsOrder();
        testNoMatchReturnsFalseAndLeavesListUnchanged();
        testEmptyArgumentReturnsFalse();
        testRemovesAllElementsWithDuplicates();
        testEmptyListReturnsFalse();
        testNullElementsAreMatchedByNullInArgument();
        testRemoveAllWithItself();
        testEqualsIsUsedForMatching();
    }

    static void testRemovesMatchingElementsAndKeepsOrder() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 2));
        Collection<?> c = Arrays.asList(2, 4);
        boolean changed = list.removeAll(c);
        check(changed, "removeAll should return true when elements are removed");
        check(list.toString().equals("[1, 3, 5]"),
              "unexpected content: " + list);
        check(list.size() == 3, "unexpected size: " + list.size());
    }

    static void testNoMatchReturnsFalseAndLeavesListUnchanged() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.removeAll(Arrays.asList(9));
        check(!changed, "removeAll should return false when nothing matches");
        check(list.toString().equals("[1, 2, 3]"),
              "list should be unchanged: " + list);
    }

    static void testEmptyArgumentReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
        boolean changed = list.removeAll(new ArrayList<Integer>());
        check(!changed, "removeAll with empty collection should return false");
        check(list.toString().equals("[1, 2]"),
              "list should be unchanged: " + list);
    }

    static void testRemovesAllElementsWithDuplicates() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 1, 1));
        boolean changed = list.removeAll(Arrays.asList(1));
        check(changed, "removeAll should return true");
        check(list.size() == 0, "list should be empty, size=" + list.size());
        check(list.isEmpty(), "list should report isEmpty");
    }

    static void testEmptyListReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>();
        boolean changed = list.removeAll(Arrays.asList(1));
        check(!changed, "removeAll on empty list should return false");
        check(list.size() == 0, "empty list should stay empty");
    }

    static void testNullElementsAreMatchedByNullInArgument() {
        List<Object> list = new ArrayList<>();
        list.add(null);
        list.add("x");
        list.add(null);
        List<Object> c = new ArrayList<>();
        c.add(null);
        boolean changed = list.removeAll(c);
        check(changed, "removeAll should remove null elements");
        check(list.size() == 1, "unexpected size: " + list.size());
        check("x".equals(list.get(0)), "remaining element should be x");
    }

    static void testRemoveAllWithItself() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.removeAll(list);
        check(changed, "removeAll(this) should return true for non-empty list");
        check(list.isEmpty(), "removeAll(this) should empty the list");
    }

    static void testEqualsIsUsedForMatching() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
        List<Long> c = Arrays.asList(1L);
        boolean changed = list.removeAll(c);
        check(!changed, "Integer(1) should not match Long(1L)");
        check(list.toString().equals("[1, 2]"),
              "list should be unchanged: " + list);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
