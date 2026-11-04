/*
 * @test
 * @summary Test LinkedList.previous() method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testPreviousOnEmptyList();
        testPreviousOnSingleElementList();
        testPreviousOnMultipleElementsList();
    }

    private static void testPreviousOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        ListIterator<String> it = list.listIterator();
        try {
            it.previous();
            throw new AssertionError("Expected NoSuchElementException for previous() on empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    private static void testPreviousOnSingleElementList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        ListIterator<String> it = list.listIterator();

        // Initially, hasPrevious() is false
        if (it.hasPrevious()) {
            throw new AssertionError("hasPrevious() should be false at start");
        }

        // Move forward to the element
        if (!it.hasNext()) {
            throw new AssertionError("hasNext() should be true at start");
        }
        String next = it.next();
        if (!"one".equals(next)) {
            throw new AssertionError("next() should return 'one'");
        }

        // Now previous() should return the element
        if (!it.hasPrevious()) {
            throw new AssertionError("hasPrevious() should be true after next()");
        }
        String prev = it.previous();
        if (!"one".equals(prev)) {
            throw new AssertionError("previous() should return 'one'");
        }
    }

    private static void testPreviousOnMultipleElementsList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ListIterator<Integer> it = list.listIterator();

        // Move iterator forward to the end
        while (it.hasNext()) {
            it.next();
        }

        // Now iterate backwards using previous()
        int expected = 3;
        while (it.hasPrevious()) {
            int val = it.previous();
            if (val != expected) {
                throw new AssertionError("Expected previous() to return " + expected + " but got " + val);
            }
            expected--;
        }

        // After iterating back to start, previous() should throw
        try {
            it.previous();
            throw new AssertionError("Expected NoSuchElementException after iterating to start");
        } catch (NoSuchElementException expectedEx) {
            // expected
        }
    }
}
