/*
 * @test
 * @summary Tests for LinkedBlockingDeque methods, focusing on removeLastOccurrence behavior.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveLastOccurrenceWithNull();
        testRemoveLastOccurrenceWithExistingElement();
        testRemoveLastOccurrenceWithNonExistingElement();
        testRemoveLastOccurrenceWithDifferentObject();
    }

    static void testRemoveLastOccurrenceWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.removeLastOccurrence(null);
        if (result != false) {
            throw new AssertionError("Expected false when removing null, got: " + result);
        }
    }

    static void testRemoveLastOccurrenceWithExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        boolean result = deque.removeLastOccurrence("a");
        if (result != true) {
            throw new AssertionError("Expected true when removing existing element 'a', got: " + result);
        }
        if (deque.contains("a")) {
            throw new AssertionError("Deque should not contain 'a' after removal.");
        }
    }

    static void testRemoveLastOccurrenceWithNonExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        deque.add("y");
        boolean result = deque.removeLastOccurrence("z");
        if (result != false) {
            throw new AssertionError("Expected false when removing non-existing element 'z', got: " + result);
        }
    }

    static void testRemoveLastOccurrenceWithDifferentObject() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("test");
        Object obj = new String("test");
        boolean result = deque.removeLastOccurrence(obj);
        if (result != true) {
            throw new AssertionError("Expected true when removing equivalent object, got: " + result);
        }
        if (deque.contains("test")) {
            throw new AssertionError("Deque should not contain 'test' after removal.");
        }
    }
}
