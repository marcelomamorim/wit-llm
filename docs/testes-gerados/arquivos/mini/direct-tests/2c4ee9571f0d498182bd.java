/*
 * @test
 * @summary Test LinkedList.ListItr.next() method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsNoSuchElementExceptionWhenNoNext();
    }

    private static void testNextReturnsElementsInOrder() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ListIterator<String> it = list.listIterator();

        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected 'a' but got " + first);
        }

        String second = it.next();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected 'b' but got " + second);
        }

        String third = it.next();
        if (!"c".equals(third)) {
            throw new AssertionError("Expected 'c' but got " + third);
        }
    }

    private static void testNextThrowsNoSuchElementExceptionWhenNoNext() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        ListIterator<Integer> it = list.listIterator();
        it.next(); // consume the only element

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() with no next element");
        }
    }
}
