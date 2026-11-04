/*
 * @test
 * @summary Test LinkedList.ListItr.previous() for expected exceptions and normal behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testPreviousNormal();
        testPreviousNoSuchElementException();
        testPreviousConcurrentModificationException();
    }

    private static void testPreviousNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ListIterator<String> it = list.listIterator(list.size());
        // previous() should return elements in reverse order
        String prev = it.previous();
        if (!"c".equals(prev)) {
            throw new AssertionError("Expected 'c', got: " + prev);
        }
        prev = it.previous();
        if (!"b".equals(prev)) {
            throw new AssertionError("Expected 'b', got: " + prev);
        }
    }

    private static void testPreviousNoSuchElementException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");

        ListIterator<String> it = list.listIterator();
        // At start, hasPrevious() is false, so previous() should throw NoSuchElementException
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling previous() at start");
        }
    }

    private static void testPreviousConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");

        ListIterator<String> it = list.listIterator();
        it.next(); // move iterator forward

        // Modify list structurally outside iterator
        list.add("z");

        boolean thrown = false;
        try {
            it.previous(); // should detect concurrent modification
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException after structural modification");
        }
    }
}
