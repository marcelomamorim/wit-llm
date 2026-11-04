/*
 * @test
 * @summary Test for removeFirstOccurrence method in ConcurrentLinkedDeque
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstOccurrenceWithNull();
        testRemoveFirstOccurrenceWithExistingElement();
        testRemoveFirstOccurrenceWithNonExistingElement();
    }

    private static void testRemoveFirstOccurrenceWithNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.removeFirstOccurrence(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null");
        }
    }

    private static void testRemoveFirstOccurrenceWithExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        String element = "test";
        deque.add(element);
        boolean result = deque.removeFirstOccurrence(element);
        if (!result) {
            throw new AssertionError("Failed to remove existing element");
        }
        if (deque.contains(element)) {
            throw new AssertionError("Element still present after removal");
        }
    }

    private static void testRemoveFirstOccurrenceWithNonExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        boolean result = deque.removeFirstOccurrence("b");
        if (result) {
            throw new AssertionError("Should not remove non-existing element");
        }
    }
}
