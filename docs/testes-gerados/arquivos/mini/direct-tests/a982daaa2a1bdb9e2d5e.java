/*
 * @test
 * @summary Test java.util.ArrayDeque next() method behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInReverseOrder();
        testNextThrowsWhenNoElements();
    }

    private static void testNextReturnsElementsInReverseOrder() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        Iterator<String> it = deque.descendingIterator();

        // next() should return elements in reverse order of insertion
        String first = it.next();
        if (!"three".equals(first)) {
            throw new AssertionError("Expected 'three' but got: " + first);
        }

        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected 'two' but got: " + second);
        }

        String third = it.next();
        if (!"one".equals(third)) {
            throw new AssertionError("Expected 'one' but got: " + third);
        }
    }

    private static void testNextThrowsWhenNoElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Iterator<String> it = deque.descendingIterator();

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
