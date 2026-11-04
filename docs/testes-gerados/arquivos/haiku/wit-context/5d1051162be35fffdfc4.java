/*
 * @test
 * @summary Witup-generated tests for ConcurrentLinkedDeque.removeLastOccurrence
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNullPointerException();
        testEmptyDequeReturnsFalse();
        testAbsentElementReturnsFalseAndKeepsContent();
        testRemovesLastOccurrenceOnly();
        testSingleElementRemoval();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<String> contentOf(ConcurrentLinkedDeque<String> deque) {
        return new ArrayList<>(deque);
    }

    static void testNullArgumentThrowsNullPointerException() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        try {
            deque.removeLastOccurrence(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(contentOf(deque).equals(Arrays.asList("a")),
              "deque content changed after null argument");
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        check(!deque.removeLastOccurrence("a"), "empty deque should return false");
        check(deque.isEmpty(), "empty deque should remain empty");
    }

    static void testAbsentElementReturnsFalseAndKeepsContent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        check(!deque.removeLastOccurrence("z"), "absent element should return false");
        check(contentOf(deque).equals(Arrays.asList("a", "b")),
              "content changed after removing absent element");
    }

    static void testRemovesLastOccurrenceOnly() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        check(deque.removeLastOccurrence("a"), "should remove an existing element");
        check(contentOf(deque).equals(Arrays.asList("a", "b")),
              "only the last occurrence should be removed");
    }

    static void testSingleElementRemoval() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        check(deque.removeLastOccurrence("x"), "should remove single element");
        check(deque.isEmpty(), "deque should be empty after removing its only element");
        check(!deque.removeLastOccurrence("x"), "second removal should return false");
    }
}
