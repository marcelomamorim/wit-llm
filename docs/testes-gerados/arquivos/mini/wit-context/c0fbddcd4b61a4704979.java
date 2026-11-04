/*
 * @test
 * @summary Test ArrayDeque.DeqIterator.next() method for correct behavior and exception
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsWhenNoElements();
        System.out.println("All tests passed.");
    }

    private static void testNextReturnsElementsInOrder() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        Iterator<String> it = deque.iterator();

        // next() should return elements in insertion order
        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected 'one', got: " + first);
        }

        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected 'two', got: " + second);
        }

        String third = it.next();
        if (!"three".equals(third)) {
            throw new AssertionError("Expected 'three', got: " + third);
        }
    }

    private static void testNextThrowsWhenNoElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Iterator<String> it = deque.iterator();

        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
