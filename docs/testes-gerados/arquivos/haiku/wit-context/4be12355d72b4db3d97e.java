/*
 * @test
 * @summary Witup-generated behavioral tests for ArrayList.set(int, E) bounds checking and return value
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetReturnsOldValueAndStoresNewValue();
        testSetAcceptsNullElement();
        testSetOutOfBoundsThrowsAndDoesNotModify();
        testSetOnEmptyListThrows();
    }

    static void testSetReturnsOldValueAndStoresNewValue() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String old = list.set(1, "c");
        check("b".equals(old), "set must return previous element");
        check("c".equals(list.get(1)), "set must store new element");
        check(list.size() == 2, "set must not change size");
        check("a".equals(list.get(0)), "set must not change other elements");
    }

    static void testSetAcceptsNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String old = list.set(0, null);
        check("a".equals(old), "set must return previous element when replacing with null");
        check(list.get(0) == null, "set must allow null element");
        check(list.size() == 2, "size must remain unchanged after setting null");
    }

    static void testSetOutOfBoundsThrowsAndDoesNotModify() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        expectIndexOutOfBounds(list, -1, "x");
        expectIndexOutOfBounds(list, 2, "x");
        expectIndexOutOfBounds(list, Integer.MAX_VALUE, "x");
        check("a".equals(list.get(0)), "failed set must not modify index 0");
        check("b".equals(list.get(1)), "failed set must not modify index 1");
        check(list.size() == 2, "failed set must not change size");
    }

    static void testSetOnEmptyListThrows() {
        ArrayList<String> list = new ArrayList<>(10);
        expectIndexOutOfBounds(list, 0, "x");
        check(list.size() == 0, "failed set on empty list must not change size");
    }

    static void expectIndexOutOfBounds(ArrayList<String> list, int index, String value) {
        try {
            list.set(index, value);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
