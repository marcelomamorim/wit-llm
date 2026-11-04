/*
 * @test
 * @summary Tests for java.util.ArrayDeque class, focusing on the next() method.
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNextWithRemainingElements();
        testNextWithNoRemainingElements();
    }

    public static void testNextWithRemainingElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        ArrayDeque.DeqIterator iterator = deque.new DeqIterator();
        try {
            String element1 = iterator.next();
            String element2 = iterator.next();
            String element3 = iterator.next();
            if (!"a".equals(element1) || !"b".equals(element2) || !"c".equals(element3)) {
                throw new AssertionError("Unexpected elements returned by next()");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextWithRemainingElements", e);
        }
    }

    public static void testNextWithNoRemainingElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        ArrayDeque.DeqIterator iterator = deque.new DeqIterator();
        // Manually set remaining to 0 to simulate exhausted iterator
        // Since remaining is private, simulate by removing all elements
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextWithNoRemainingElements", e);
        }
    }
}
