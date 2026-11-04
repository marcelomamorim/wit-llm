/*
 * @test
 * @summary Test ConcurrentLinkedDeque.removeLastOccurrence behavior including null input and element removal
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveLastOccurrenceNull();
        testRemoveLastOccurrenceNotPresent();
        testRemoveLastOccurrencePresent();
        testRemoveLastOccurrenceMultiple();
    }

    private static void testRemoveLastOccurrenceNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean npeThrown = false;
        try {
            deque.removeLastOccurrence(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when removing null");
        }
    }

    private static void testRemoveLastOccurrenceNotPresent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.removeLastOccurrence("x");
        if (removed) {
            throw new AssertionError("removeLastOccurrence should return false when element not present");
        }
        // Confirm size unchanged
        if (deque.size() != 3) {
            throw new AssertionError("Size should remain 3 after failed removal");
        }
    }

    private static void testRemoveLastOccurrencePresent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.removeLastOccurrence("b");
        if (!removed) {
            throw new AssertionError("removeLastOccurrence should return true when element present");
        }
        // Confirm "b" is removed and others remain
        if (deque.contains("b")) {
            throw new AssertionError("Element 'b' should be removed");
        }
        if (deque.size() != 2) {
            throw new AssertionError("Size should be 2 after removal");
        }
    }

    private static void testRemoveLastOccurrenceMultiple() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        deque.add("b");
        deque.add("d");
        // removeLastOccurrence should remove the last occurrence of "b"
        boolean removed = deque.removeLastOccurrence("b");
        if (!removed) {
            throw new AssertionError("removeLastOccurrence should return true when element present");
        }
        // The last occurrence of "b" was removed, one "b" should remain
        int countB = 0;
        for (String s : deque) {
            if ("b".equals(s)) countB++;
        }
        if (countB != 1) {
            throw new AssertionError("Exactly one 'b' should remain after removal");
        }
    }
}
