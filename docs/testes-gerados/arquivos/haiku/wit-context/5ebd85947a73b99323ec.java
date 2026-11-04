/*
 * @test
 * @summary Witup-derived behavioral tests for LinkedList.element()
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyListThrowsNoSuchElement();
        testReturnsHeadWithoutRemoving();
        testNullHeadIsReturned();
        testAddFirstChangesHead();
        testSingleElementThenRemovedThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyListThrowsNoSuchElement() {
        LinkedList<String> list = new LinkedList<String>();
        boolean thrown = false;
        try {
            list.element();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "element() on empty list must throw NoSuchElementException");
    }

    static void testReturnsHeadWithoutRemoving() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("a".equals(list.element()), "element() must return the head");
        check(list.size() == 3, "element() must not remove the head");
        check("a".equals(list.element()), "repeated element() must return the same head");
        check("a".equals(list.getFirst()), "getFirst() must agree with element()");
    }

    static void testNullHeadIsReturned() {
        LinkedList<String> list = new LinkedList<String>();
        list.add(null);
        list.add("x");
        check(list.element() == null, "element() must return null when head is null");
        check(list.size() == 2, "element() must not change size");
    }

    static void testAddFirstChangesHead() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("second");
        list.addFirst("first");
        check("first".equals(list.element()), "element() must reflect addFirst");
    }

    static void testSingleElementThenRemovedThrows() {
        LinkedList<String> list = new LinkedList<String>();
        list.add("only");
        check("only".equals(list.element()), "element() must return the only element");
        list.removeFirst();
        boolean thrown = false;
        try {
            list.element();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "element() after emptying the list must throw NoSuchElementException");
    }
}
