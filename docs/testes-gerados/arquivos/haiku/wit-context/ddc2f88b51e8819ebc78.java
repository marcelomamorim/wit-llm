/*
 * @test
 * @summary Verify ArrayList.add(int, E) bounds checking and observable behavior at valid and invalid indexes
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddToEmptyListAtZero();
        testAddAtSizeAppends();
        testAddAtFrontInserts();
        testAddNullElement();
        testNegativeIndexThrowsAndLeavesListUnchanged();
        testIndexGreaterThanSizeThrowsAndLeavesListUnchanged();
        testIndexEqualsSizePlusOneThrowsOnEmptyList();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void assertIndexOutOfBounds(List<String> list, int index, String value) {
        try {
            list.add(index, value);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException for index " + index);
    }

    static void testAddToEmptyListAtZero() {
        List<String> list = new ArrayList<>();
        list.add(0, "a");
        check(list.size() == 1, "size after add(0) on empty list");
        check("a".equals(list.get(0)), "element after add(0) on empty list");
    }

    static void testAddAtSizeAppends() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add(2, "c");
        check(list.size() == 3, "size after add(size, e)");
        check("a".equals(list.get(0)), "first element after add(size, e)");
        check("b".equals(list.get(1)), "second element after add(size, e)");
        check("c".equals(list.get(2)), "appended element");
    }

    static void testAddAtFrontInserts() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add(0, "z");
        check(list.size() == 4, "size after add(0, e)");
        check("z".equals(list.get(0)), "inserted front element");
        check("a".equals(list.get(1)), "shifted element 1");
        check("b".equals(list.get(2)), "shifted element 2");
        check("c".equals(list.get(3)), "shifted element 3");
    }

    static void testAddNullElement() {
        List<String> list = new ArrayList<>();
        list.add(0, null);
        check(list.size() == 1, "size after adding null");
        check(list.get(0) == null, "null element should be stored");
    }

    static void testNegativeIndexThrowsAndLeavesListUnchanged() {
        List<String> list = new ArrayList<>();
        list.add("a");
        assertIndexOutOfBounds(list, -1, "x");
        check(list.size() == 1, "list size must not change after rejected negative index");
        check("a".equals(list.get(0)), "list content must not change after rejected negative index");
    }

    static void testIndexGreaterThanSizeThrowsAndLeavesListUnchanged() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        assertIndexOutOfBounds(list, 3, "c");
        check(list.size() == 2, "list size must not change after rejected index greater than size");
        check("a".equals(list.get(0)), "element 0 must be preserved");
        check("b".equals(list.get(1)), "element 1 must be preserved");
    }

    static void testIndexEqualsSizePlusOneThrowsOnEmptyList() {
        List<String> list = new ArrayList<>();
        assertIndexOutOfBounds(list, 1, "x");
        check(list.isEmpty(), "empty list must remain empty after rejected index");
    }
}
