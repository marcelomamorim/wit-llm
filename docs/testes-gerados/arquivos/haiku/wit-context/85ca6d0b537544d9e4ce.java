/*
 * @test
 * @summary Verify LinkedList.getLast() returns the last element and throws NoSuchElementException when the list is empty
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyListThrows();
        testSingleElement();
        testMultipleElements();
        testLastNullElement();
        testAfterRemoveLast();
        testFromCollectionConstructor();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.getLast();
            throw new RuntimeException("expected NoSuchElementException on empty list");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testSingleElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        check("a".equals(list.getLast()), "getLast on single element list");
    }

    static void testMultipleElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        check(list.getLast().equals(3), "getLast should return last added element");
        check(list.size() == 3, "getLast must not modify the list");
    }

    static void testLastNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add(null);
        check(list.getLast() == null, "getLast should return null when last element is null");
    }

    static void testAfterRemoveLast() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.removeLast();
        check("a".equals(list.getLast()), "getLast should reflect new last element after removeLast");
        list.removeLast();
        try {
            list.getLast();
            throw new RuntimeException("expected NoSuchElementException after emptying list");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testFromCollectionConstructor() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("p", "q", "r"));
        check("r".equals(list.getLast()), "getLast on list built from collection");
    }
}
