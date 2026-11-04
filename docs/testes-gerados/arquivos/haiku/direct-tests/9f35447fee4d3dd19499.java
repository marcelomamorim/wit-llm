/*
 * @test
 * @summary Tests observable behavior of LinkedList.removeFirst()
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyListThrows();
        testSingleElementThenEmpty();
        testFifoOrder();
        testNullElementIsReturned();
        testAddFirstThenRemoveFirst();
        testRemoveFirstAfterDrainingThrows();
    }

    static void testEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "removeFirst on empty list must throw NoSuchElementException");
        check(list.isEmpty(), "list must remain empty after failed removeFirst");
        check(list.size() == 0, "size must be 0 after failed removeFirst");
    }

    static void testSingleElementThenEmpty() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        String r = list.removeFirst();
        check("a".equals(r), "removeFirst must return the only element");
        check(list.isEmpty(), "list must be empty after removing its only element");
        check(list.size() == 0, "size must be 0 after removing its only element");
    }

    static void testFifoOrder() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        check(list.removeFirst() == 1, "first removeFirst must return 1");
        check(list.size() == 2, "size must be 2 after one removal");
        check(list.removeFirst() == 2, "second removeFirst must return 2");
        check(list.size() == 1, "size must be 1 after two removals");
        check(list.removeFirst() == 3, "third removeFirst must return 3");
        check(list.isEmpty(), "list must be empty after three removals");
    }

    static void testNullElementIsReturned() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("b");
        String r = list.removeFirst();
        check(r == null, "removeFirst must return null when the head element is null");
        check(list.size() == 1, "size must be 1 after removing null head");
        check("b".equals(list.getFirst()), "new head must be \"b\"");
    }

    static void testAddFirstThenRemoveFirst() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.addFirst("y");
        check("y".equals(list.removeFirst()), "removeFirst must return element added with addFirst");
        check("x".equals(list.removeFirst()), "next removeFirst must return remaining element");
        check(list.isEmpty(), "list must be empty at the end");
    }

    static void testRemoveFirstAfterDrainingThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(42);
        list.removeFirst();
        boolean thrown = false;
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "removeFirst after draining the list must throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
