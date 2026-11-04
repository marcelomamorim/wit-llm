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
        testNextReturnsElementsInOrder();
        testNextThrowsWhenNoElements();
    }

    private static void testNextReturnsElementsInOrder() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("first");
        deque.add("second");
        deque.add("third");

        Iterator<String> it = deque.iterator();

        // next() should return elements in insertion order
        String first = it.next();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first', got: " + first);
        }

        String second = it.next();
        if (!"second".equals(second)) {
            throw new AssertionError("Expected 'second', got: " + second);
        }

        String third = it.next();
        if (!"third".equals(third)) {
            throw new AssertionError("Expected 'third', got: " + third);
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
