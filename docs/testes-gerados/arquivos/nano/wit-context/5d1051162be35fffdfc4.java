/*
 * @test
 * @summary Test for removeLastOccurrence method in ConcurrentLinkedDeque
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveLastOccurrenceWithNull();
        testRemoveLastOccurrenceWithAbsentElement();
        testRemoveLastOccurrenceWithExistingElement();
    }

    private static void testRemoveLastOccurrenceWithNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.removeLastOccurrence(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null");
        }
    }

    private static void testRemoveLastOccurrenceWithAbsentElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        boolean result = deque.removeLastOccurrence("z");
        if (result != false) {
            throw new AssertionError("Expected false when element not present");
        }
    }

    private static void testRemoveLastOccurrenceWithExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        boolean result = deque.removeLastOccurrence("a");
        if (result != true) {
            throw new AssertionError("Expected true when element is removed");
        }
        if (deque.contains("a")) {
            throw new AssertionError("Element "a" should be removed from deque");
        }
    }
}
