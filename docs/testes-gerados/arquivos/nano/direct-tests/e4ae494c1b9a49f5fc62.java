/*
 * @test    @summary Test removeFirstOccurrence method of ConcurrentLinkedDeque
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstOccurrenceExists();
        testRemoveFirstOccurrenceDoesNotExist();
        testRemoveFirstOccurrenceNull();
    }

    static void testRemoveFirstOccurrenceExists() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.removeFirstOccurrence("b");
        if (!removed) {
            throw new AssertionError("Expected to remove element 'b'");
        }
        if (deque.contains("b")) {
            throw new AssertionError("Deque should not contain 'b' after removal");
        }
    }

    static void testRemoveFirstOccurrenceDoesNotExist() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");
        boolean removed = deque.removeFirstOccurrence("z");
        if (removed) {
            throw new AssertionError("Should not remove non-existent element 'z'");
        }
        if (!deque.contains("x") || !deque.contains("y")) {
            throw new AssertionError("Deque should still contain 'x' and 'y'");
        }
    }

    static void testRemoveFirstOccurrenceNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("m");
        try {
            deque.removeFirstOccurrence(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
