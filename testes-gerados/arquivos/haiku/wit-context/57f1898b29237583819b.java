/*
 * @test
 * @summary Witup-derived checks for LinkedList.getFirst: empty list throws NoSuchElementException, non-empty list returns head
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyListThrowsNoSuchElementException();
        testNonEmptyListReturnsHead();
        testNullHeadIsReturned();
        testHeadAfterAddFirst();
    }

    static void testEmptyListThrowsNoSuchElementException() {
        LinkedList<String> list = new LinkedList<String>();
        boolean thrown = false;
        try {
            list.getFirst();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getFirst on empty list did not throw NoSuchElementException");
        }
    }

    static void testNonEmptyListReturnsHead() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("a");
        list.add("b");
        String first = list.getFirst();
        if (!"a".equals(first)) {
            throw new RuntimeException("expected a but got " + first);
        }
        if (list.size() != 2) {
            throw new RuntimeException("getFirst must not modify the list, size=" + list.size());
        }
    }

    static void testNullHeadIsReturned() {
        LinkedList<String> list = new LinkedList<String>();
        list.add(null);
        list.add("x");
        String first = list.getFirst();
        if (first != null) {
            throw new RuntimeException("expected null head but got " + first);
        }
    }

    static void testHeadAfterAddFirst() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("b");
        list.addFirst("a");
        String first = list.getFirst();
        if (!"a".equals(first)) {
            throw new RuntimeException("expected a after addFirst but got " + first);
        }
    }
}
