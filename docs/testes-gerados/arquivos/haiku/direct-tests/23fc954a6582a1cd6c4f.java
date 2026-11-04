/*
 * @test
 * @summary Black-box tests for ArrayList.remove(int index): returned value, shifting, size update, null elements and IndexOutOfBoundsException for invalid indexes.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testRemoveReturnsElementAndShifts() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        check("b".equals(removed), "remove(1) should return 'b'");
        check(list.size() == 2, "size should be 2 after remove");
        check("a".equals(list.get(0)), "index 0 should still be 'a'");
        check("c".equals(list.get(1)), "element after removed index should shift to index 1");
    }

    static void testRemoveFirstAndLast() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        check(list.remove(0) == 10, "remove(0) should return 10");
        check(list.size() == 2, "size should be 2 after removing first");
        check(list.get(0) == 20 && list.get(1) == 30, "contents should be [20, 30]");
        check(list.remove(1) == 30, "remove(last) should return 30");
        check(list.size() == 1 && list.get(0) == 20, "contents should be [20]");
    }

    static void testRemoveDuplicateRemovesByIndexOnly() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("x");
        list.add("y");
        String removed = list.remove(0);
        check("x".equals(removed), "remove(0) should return 'x'");
        check(list.size() == 2, "size should be 2");
        check("x".equals(list.get(0)) && "y".equals(list.get(1)), "contents should be [x, y]");
    }

    static void testRemoveNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("z");
        String removed = list.remove(0);
        check(removed == null, "remove(0) should return null element");
        check(list.size() == 1, "size should be 1 after removing null");
        check("z".equals(list.get(0)), "remaining element should be 'z'");
    }

    static void testRemoveOnlyElementMakesEmpty() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");
        check("only".equals(list.remove(0)), "remove(0) should return 'only'");
        check(list.isEmpty(), "list should be empty");
    }

    static void expectIndexOutOfBounds(ArrayList<String> list, int index, String message) {
        boolean thrown = false;
        try {
            list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, message);
    }

    static void testInvalidIndexes() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        expectIndexOutOfBounds(list, -1, "remove(-1) should throw IndexOutOfBoundsException");
        expectIndexOutOfBounds(list, 2, "remove(size) should throw IndexOutOfBoundsException");
        expectIndexOutOfBounds(list, 5, "remove(index > size) should throw IndexOutOfBoundsException");
        check(list.size() == 2, "failed remove must not change size");
        check("a".equals(list.get(0)) && "b".equals(list.get(1)), "failed remove must not change contents");
    }

    static void testRemoveFromEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        expectIndexOutOfBounds(list, 0, "remove(0) on empty list should throw IndexOutOfBoundsException");
        check(list.isEmpty(), "empty list should remain empty");
    }

    public static void main(String[] args) {
        testRemoveReturnsElementAndShifts();
        testRemoveFirstAndLast();
        testRemoveDuplicateRemovesByIndexOnly();
        testRemoveNullElement();
        testRemoveOnlyElementMakesEmpty();
        testInvalidIndexes();
        testRemoveFromEmptyList();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
