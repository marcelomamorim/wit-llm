/*
 * @test
 * @summary WITUP-derived index validation checks for LinkedList.add(int, E)
 * @run main LinkedListWitupTest
 */
import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testAddAtBoundaries();
        testInvalidIndexRejected();
        testNullElement();
    }

    static void testAddAtBoundaries() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "a");
        check("[a]".equals(list.toString()), "add(0) on empty list");
        list.add(0, "b");
        check("[b, a]".equals(list.toString()), "add(0) at head");
        list.add(list.size(), "c");
        check("[b, a, c]".equals(list.toString()), "add(size) appends");
        list.add(1, "x");
        check("[b, x, a, c]".equals(list.toString()), "add in middle");
    }

    static void testInvalidIndexRejected() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        expectIOOBE(list, -1, "z");
        expectIOOBE(list, list.size() + 1, "z");
        expectIOOBE(list, Integer.MIN_VALUE, "z");
        check("[a, b]".equals(list.toString()), "invalid add must not modify list");

        LinkedList<String> empty = new LinkedList<>();
        expectIOOBE(empty, 1, "z");
        check(empty.size() == 0, "invalid add on empty list must not modify it");
    }

    static void testNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, null);
        check(list.size() == 1 && list.get(0) == null, "null element is permitted");
    }

    static void expectIOOBE(LinkedList<String> list, int index, String value) {
        try {
            list.add(index, value);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
