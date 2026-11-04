/*
 * @test
 * @summary Verifies LinkedList.element() returns the head element without removing it and throws NoSuchElementException when empty
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testElementReturnsHeadWithoutRemoving();
        testElementAfterAddFirst();
        testElementOnCopiedCollection();
        testElementReturnsNullHead();
        testElementAfterRemoveFirst();
        testElementOnEmptyListThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testElementReturnsHeadWithoutRemoving() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("a".equals(list.element()), "element() should return head 'a'");
        check(list.size() == 3, "element() must not remove the head");
        check("a".equals(list.element()), "second element() call should still return 'a'");
    }

    static void testElementAfterAddFirst() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(2);
        list.addFirst(1);
        check(list.element() == 1, "element() should reflect addFirst");
    }

    static void testElementOnCopiedCollection() {
        List<String> source = Arrays.asList("x", "y");
        LinkedList<String> list = new LinkedList<>(source);
        check("x".equals(list.element()), "element() on copied collection should return first source element");
        check(list.size() == 2, "copy constructor list size should be preserved");
    }

    static void testElementReturnsNullHead() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("b");
        check(list.element() == null, "element() should return null when head is null");
        check(list.size() == 2, "element() must not change size when head is null");
    }

    static void testElementAfterRemoveFirst() {
        LinkedList<String> list = new LinkedList<>();
        list.add("p");
        list.add("q");
        list.removeFirst();
        check("q".equals(list.element()), "element() should return new head after removeFirst");
    }

    static void testElementOnEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.element();
            throw new RuntimeException("element() on empty list should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
