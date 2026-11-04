/*
 * @test
 * @summary WITUP-derived behavioral tests for LinkedList.set(int, E)
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testSetReturnsOldValueAndUpdates();
        testSetAcceptsNullElement();
        testSetOutOfBoundsThrowsAndKeepsList();
        testSetOnEmptyListThrows();
    }

    static void testSetReturnsOldValueAndUpdates() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String old = list.set(1, "B");
        check("B-old".length() > 0 && "b".equals(old), "set should return previous element");
        check("B".equals(list.get(1)), "set should store new element");
        check(list.size() == 3, "set must not change size");
        check("a".equals(list.get(0)) && "c".equals(list.get(2)), "set must not affect other indices");
    }

    static void testSetAcceptsNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        String old = list.set(0, null);
        check("x".equals(old), "set should return previous non-null element");
        check(list.get(0) == null, "set should accept null element");
        check(list.size() == 1, "size should remain unchanged");
    }

    static void testSetOutOfBoundsThrowsAndKeepsList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("p");
        list.add("q");
        expectIOOBE(list, -1);
        expectIOOBE(list, 2);
        expectIOOBE(list, Integer.MIN_VALUE);
        expectIOOBE(list, Integer.MAX_VALUE);
        check(list.size() == 2, "failed set must not change size");
        check("p".equals(list.get(0)) && "q".equals(list.get(1)), "failed set must not change elements");
    }

    static void testSetOnEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        expectIOOBE(list, 0);
        expectIOOBE(list, -1);
        check(list.isEmpty(), "failed set on empty list must leave it empty");
    }

    static void expectIOOBE(LinkedList<String> list, int index) {
        try {
            list.set(index, "v");
        } catch (IndexOutOfBoundsException expected) {
            return;
        }
        throw new AssertionError("Expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
