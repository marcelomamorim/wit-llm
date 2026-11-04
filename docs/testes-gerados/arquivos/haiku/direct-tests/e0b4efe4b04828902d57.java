/*
 * @test
 * @summary Witup-style tests for LinkedList.get(int)
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testGetReturnsElementsInOrder();
        testGetReturnsNullElement();
        testGetOnEmptyListThrows();
        testGetNegativeIndexThrows();
        testGetIndexEqualToSizeThrows();
        testGetFromCollectionConstructor();
        testGetAfterFirstAndLastModifications();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIndexOutOfBounds(LinkedList<?> list, int index, String message) {
        try {
            list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testGetReturnsElementsInOrder() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("a".equals(list.get(0)), "get(0) should return first element");
        check("b".equals(list.get(1)), "get(1) should return second element");
        check("c".equals(list.get(2)), "get(2) should return last element");
    }

    static void testGetReturnsNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("x");
        check(list.get(0) == null, "get(0) should return null element");
        check("x".equals(list.get(1)), "get(1) should return non-null element");
    }

    static void testGetOnEmptyListThrows() {
        expectIndexOutOfBounds(new LinkedList<String>(), 0,
                "get(0) on empty list should throw IndexOutOfBoundsException");
    }

    static void testGetNegativeIndexThrows() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        expectIndexOutOfBounds(list, -1,
                "get(-1) should throw IndexOutOfBoundsException");
    }

    static void testGetIndexEqualToSizeThrows() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        expectIndexOutOfBounds(list, 2,
                "get(size) should throw IndexOutOfBoundsException");
    }

    static void testGetFromCollectionConstructor() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3));
        check(list.get(0).intValue() == 1, "get(0) mismatch after collection constructor");
        check(list.get(2).intValue() == 3, "get(2) mismatch after collection constructor");
    }

    static void testGetAfterFirstAndLastModifications() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3));
        list.addFirst(0);
        check(list.get(0).intValue() == 0, "get(0) should return element added first");
        list.removeLast();
        check(list.get(2).intValue() == 2, "get(2) should return new last element");
        expectIndexOutOfBounds(list, 3,
                "get(size) after removeLast should throw IndexOutOfBoundsException");
    }
}
