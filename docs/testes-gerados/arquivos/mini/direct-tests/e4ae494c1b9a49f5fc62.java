/*
 * @test
 * @summary Test ConcurrentLinkedDeque.removeFirstOccurrence(Object) behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveFirstOccurrenceNullArgument();
        testRemoveFirstOccurrenceElementPresent();
        testRemoveFirstOccurrenceElementAbsent();
        testRemoveFirstOccurrenceMultipleElements();
    }

    static void testRemoveFirstOccurrenceNullArgument() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.removeFirstOccurrence(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveFirstOccurrenceElementPresent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.removeFirstOccurrence("b");
        if (!removed) {
            throw new Error("Expected to remove element 'b'");
        }
        if (deque.contains("b")) {
            throw new Error("Element 'b' should no longer be present");
        }
        if (deque.size() != 2) {
            throw new Error("Expected size 2 after removal, got " + deque.size());
        }
    }

    static void testRemoveFirstOccurrenceElementAbsent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");
        boolean removed = deque.removeFirstOccurrence("z");
        if (removed) {
            throw new Error("Expected false when removing absent element 'z'");
        }
        if (deque.size() != 2) {
            throw new Error("Size should remain 2 when removing absent element, got " + deque.size());
        }
    }

    static void testRemoveFirstOccurrenceMultipleElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("dup");
        deque.add("dup");
        deque.add("dup");
        boolean removed = deque.removeFirstOccurrence("dup");
        if (!removed) {
            throw new Error("Expected to remove first occurrence of 'dup'");
        }
        int count = 0;
        for (String s : deque) {
            if ("dup".equals(s)) count++;
        }
        if (count != 2) {
            throw new Error("Expected 2 occurrences of 'dup' after removal, got " + count);
        }
    }
}
