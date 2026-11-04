/*
 * @test
 * @summary Verifies LinkedList.pop() returns and removes the first element, handles null elements, and throws NoSuchElementException when empty
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testPopReturnsHeadAndRemovesIt();
        testPopOnSingleElementEmptiesList();
        testPopReturnsNullHeadElement();
        testPopOnEmptyListThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPopReturnsHeadAndRemovesIt() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String popped = list.pop();
        check("a".equals(popped), "pop() should return head 'a' but returned " + popped);
        check(list.size() == 2, "size after pop should be 2 but was " + list.size());
        check("b".equals(list.getFirst()), "new head should be 'b'");
    }

    static void testPopOnSingleElementEmptiesList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        String popped = list.pop();
        check("x".equals(popped), "pop() should return 'x' but returned " + popped);
        check(list.isEmpty(), "list should be empty after popping its only element");
    }

    static void testPopReturnsNullHeadElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("y");
        String popped = list.pop();
        check(popped == null, "pop() should return null head element");
        check(list.size() == 1, "size after pop should be 1 but was " + list.size());
        check("y".equals(list.getFirst()), "remaining head should be 'y'");
    }

    static void testPopOnEmptyListThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.pop();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "pop() on empty list should throw NoSuchElementException");
    }
}
