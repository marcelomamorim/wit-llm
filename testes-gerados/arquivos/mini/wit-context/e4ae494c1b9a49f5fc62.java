/*
 * @test
 * @summary Test ConcurrentLinkedDeque.removeFirstOccurrence behavior including null argument
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveFirstOccurrenceNull();
        testRemoveFirstOccurrencePresent();
        testRemoveFirstOccurrenceAbsent();
    }

    private static void testRemoveFirstOccurrenceNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean npeThrown = false;
        try {
            deque.removeFirstOccurrence(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when removing null");
        }
    }

    private static void testRemoveFirstOccurrencePresent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.removeFirstOccurrence("b");
        if (!removed) {
            throw new AssertionError("Expected to remove existing element 'b'");
        }
        if (deque.contains("b")) {
            throw new AssertionError("Element 'b' should no longer be present after removal");
        }
        // Ensure other elements remain
        if (!deque.contains("a") || !deque.contains("c")) {
            throw new AssertionError("Other elements should remain after removal");
        }
    }

    private static void testRemoveFirstOccurrenceAbsent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");
        boolean removed = deque.removeFirstOccurrence("z");
        if (removed) {
            throw new AssertionError("Expected false when removing non-existing element 'z'");
        }
        // Ensure original elements remain
        if (!deque.contains("x") || !deque.contains("y")) {
            throw new AssertionError("Original elements should remain unchanged");
        }
    }
}
