/* @test    @summary Tests for java.util.ArrayDeque.next() method behavior and exception handling    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNextWithRemainingElements();
        testNextWithNoRemaining();
    }

    static void testNextWithRemainingElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        // Use descending iterator to set cursor at start
        var iterator = deque.descendingIterator();
        // Move iterator to first element
        iterator.next();
        // Access internal cursor via reflection or assume initial position
        // Since cursor is internal, simulate by calling next() and expecting element
        String element = null;
        try {
            element = iterator.next();
        } catch (Exception e) {
            // Fail the test if exception occurs
            throw new AssertionError("Unexpected exception: " + e);
        }
        if (element == null) {
            throw new AssertionError("Expected element, got null");
        }
    }

    static void testNextWithNoRemaining() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        // Use descending iterator
        var iterator = deque.descendingIterator();
        // Move iterator to end
        // No elements, so calling next() should throw NoSuchElementException
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
