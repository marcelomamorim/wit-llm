/*
 * @test
 * @summary Verifies LinkedList.getLast returns the last element and throws NoSuchElementException when empty
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyListThrows();
        testSingleElement();
        testReturnsLastAfterAddLast();
        testReturnsLastAfterAddFirst();
        testAfterRemoveLast();
        testNullAsLastElement();
        testFromCollectionConstructor();
        testGetLastDoesNotModifyList();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testEmptyListThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.getLast();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "getLast on empty list must throw NoSuchElementException");
    }

    static void testSingleElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(42);
        check(list.getLast().equals(42), "single element must be returned as last");
    }

    static void testReturnsLastAfterAddLast() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.addLast(3);
        check(list.getLast().intValue() == 3, "getLast must return the element added last");
    }

    static void testReturnsLastAfterAddFirst() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.addFirst(0);
        check(list.getLast().intValue() == 1, "addFirst must not change the last element");
    }

    static void testAfterRemoveLast() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(10, 20, 30));
        list.removeLast();
        check(list.getLast().intValue() == 20, "after removeLast, getLast must return the new last element");
    }

    static void testNullAsLastElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(5);
        list.add(null);
        check(list.getLast() == null, "null stored as last element must be returned, not treated as empty");
    }

    static void testFromCollectionConstructor() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(7, 8, 9));
        check(list.getLast().intValue() == 9, "constructor from collection must preserve order for getLast");
    }

    static void testGetLastDoesNotModifyList() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3));
        Integer first = list.getLast();
        Integer second = list.getLast();
        check(first.equals(second), "repeated getLast calls must return the same element");
        check(list.size() == 3, "getLast must not change the size of the list");
        check(list.getFirst().intValue() == 1, "getLast must not change the first element");
    }
}
