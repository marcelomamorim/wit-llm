/*
 * @test
 * @summary Test ConcurrentLinkedDeque.removeLastOccurrence(Object) behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveLastOccurrenceNullArgument();
        testRemoveLastOccurrenceEmptyDeque();
        testRemoveLastOccurrenceSingleElementMatch();
        testRemoveLastOccurrenceSingleElementNoMatch();
        testRemoveLastOccurrenceMultipleElements();
        testRemoveLastOccurrenceMultipleOccurrences();
    }

    static void testRemoveLastOccurrenceNullArgument() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.removeLastOccurrence(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveLastOccurrenceEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean removed = deque.removeLastOccurrence("foo");
        if (removed) {
            throw new Error("removeLastOccurrence should return false on empty deque");
        }
    }

    static void testRemoveLastOccurrenceSingleElementMatch() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("foo");
        boolean removed = deque.removeLastOccurrence("foo");
        if (!removed) {
            throw new Error("removeLastOccurrence should return true when element is present");
        }
        if (!deque.isEmpty()) {
            throw new Error("Deque should be empty after removing the only element");
        }
    }

    static void testRemoveLastOccurrenceSingleElementNoMatch() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("foo");
        boolean removed = deque.removeLastOccurrence("bar");
        if (removed) {
            throw new Error("removeLastOccurrence should return false when element not present");
        }
        if (deque.isEmpty()) {
            throw new Error("Deque should not be empty when element not removed");
        }
    }

    static void testRemoveLastOccurrenceMultipleElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.removeLastOccurrence("b");
        if (!removed) {
            throw new Error("removeLastOccurrence should return true when element is present");
        }
        if (deque.size() != 2) {
            throw new Error("Deque size should be 2 after removal");
        }
        if (deque.contains("b")) {
            throw new Error("Deque should not contain removed element");
        }
    }

    static void testRemoveLastOccurrenceMultipleOccurrences() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");
        deque.add("x");
        deque.add("z");
        // removeLastOccurrence should remove the last occurrence of "x"
        boolean removed = deque.removeLastOccurrence("x");
        if (!removed) {
            throw new Error("removeLastOccurrence should return true when element is present");
        }
        // The deque should still contain one "x" (the first one)
        int countX = 0;
        for (String s : deque) {
            if ("x".equals(s)) countX++;
        }
        if (countX != 1) {
            throw new Error("Deque should contain exactly one 'x' after removing last occurrence");
        }
    }
}
