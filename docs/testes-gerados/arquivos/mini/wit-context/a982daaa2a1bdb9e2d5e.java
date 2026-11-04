/*
 * @test
 * @summary Test ArrayDeque.DescendingIterator.next() method for NoSuchElementException and normal behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testNextThrowsNoSuchElementException();
        testNextReturnsElementsInDescendingOrder();
    }

    private static void testNextThrowsNoSuchElementException() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        // Create descending iterator on empty deque
        java.util.Iterator<Integer> descIter = deque.descendingIterator();
        // The iterator has no elements, next() should throw NoSuchElementException
        try {
            descIter.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testNextReturnsElementsInDescendingOrder() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("first");
        deque.add("second");
        deque.add("third");

        java.util.Iterator<String> descIter = deque.descendingIterator();

        // The descending iterator should return elements in reverse order of insertion
        String firstReturned = descIter.next();
        if (!"third".equals(firstReturned)) {
            throw new AssertionError("Expected 'third' but got: " + firstReturned);
        }

        String secondReturned = descIter.next();
        if (!"second".equals(secondReturned)) {
            throw new AssertionError("Expected 'second' but got: " + secondReturned);
        }

        String thirdReturned = descIter.next();
        if (!"first".equals(thirdReturned)) {
            throw new AssertionError("Expected 'first' but got: " + thirdReturned);
        }

        // After all elements are returned, next() should throw NoSuchElementException
        try {
            descIter.next();
            throw new AssertionError("Expected NoSuchElementException after all elements iterated");
        } catch (NoSuchElementException expected) {
            // Expected
        }
    }
}
