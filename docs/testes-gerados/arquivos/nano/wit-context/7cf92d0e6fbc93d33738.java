/*
 * @test
 * @summary Test for removeFirstOccurrence method in LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstOccurrenceWithNull();
        testRemoveFirstOccurrenceWithExistingElement();
        testRemoveFirstOccurrenceWithNonExistingElement();
    }

    static void testRemoveFirstOccurrenceWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = false;
        try {
            result = deque.removeFirstOccurrence(null);
        } catch (NullPointerException e) {
            // Expected exception
        }
        if (result != false) {
            throw new AssertionError("Expected false when removing null, but got: " + result);
        }
    }

    static void testRemoveFirstOccurrenceWithExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        boolean result = deque.removeFirstOccurrence("element1");
        if (result != true) {
            throw new AssertionError("Expected true when removing existing element, but got: " + result);
        }
        if (deque.contains("element1")) {
            throw new AssertionError("Element1 should be removed from deque.");
        }
    }

    static void testRemoveFirstOccurrenceWithNonExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        boolean result = deque.removeFirstOccurrence("nonexistent");
        if (result != false) {
            throw new AssertionError("Expected false when removing non-existing element, but got: " + result);
        }
        if (deque.size() != 2) {
            throw new AssertionError("Deque size should remain 2, but got: " + deque.size());
        }
    }
}
