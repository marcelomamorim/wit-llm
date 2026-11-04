/*
 * @test
 * @summary LinkedList.remove() removes and returns the head element, throws NoSuchElementException when empty
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        removeReturnsHeadInOrder();
        removeAllowsNullElement();
        removeOnEmptyListThrows();
        removeAfterDrainingThrows();
    }

    static void removeReturnsHeadInOrder() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("a".equals(list.remove()), "first remove should return head 'a'");
        check(list.size() == 2, "size should be 2 after one remove");
        check("b".equals(list.remove()), "second remove should return 'b'");
        check("c".equals(list.remove()), "third remove should return 'c'");
        check(list.isEmpty(), "list should be empty after removing all elements");
    }

    static void removeAllowsNullElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("x");
        check(list.remove() == null, "remove should return null head element");
        check(list.size() == 1, "size should be 1 after removing null head");
        check("x".equals(list.remove()), "next remove should return 'x'");
    }

    static void removeOnEmptyListThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.remove();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "remove() on empty list should throw NoSuchElementException");
    }

    static void removeAfterDrainingThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        check(list.remove() == 1, "remove should return 1");
        boolean thrown = false;
        try {
            list.remove();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "remove() after draining should throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
