/*
 * @test
 * @summary Test LinkedBlockingDeque.removeLastOccurrence behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveLastOccurrenceNull();
        testRemoveLastOccurrenceNotPresent();
        testRemoveLastOccurrencePresentSingle();
        testRemoveLastOccurrencePresentMultiple();
    }

    static void testRemoveLastOccurrenceNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        // removeLastOccurrence(null) must return false without exception
        boolean removed = deque.removeLastOccurrence(null);
        if (removed) {
            throw new RuntimeException("removeLastOccurrence(null) should return false");
        }
    }

    static void testRemoveLastOccurrenceNotPresent() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        // removeLastOccurrence of element not present returns false
        boolean removed = deque.removeLastOccurrence("c");
        if (removed) {
            throw new RuntimeException("removeLastOccurrence of non-present element should return false");
        }
        // deque remains unchanged
        if (deque.size() != 2 || !deque.contains("a") || !deque.contains("b")) {
            throw new RuntimeException("Deque modified incorrectly when removing non-present element");
        }
    }

    static void testRemoveLastOccurrencePresentSingle() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        // removeLastOccurrence of present element returns true
        boolean removed = deque.removeLastOccurrence("x");
        if (!removed) {
            throw new RuntimeException("removeLastOccurrence of present element should return true");
        }
        // deque is empty after removal
        if (!deque.isEmpty()) {
            throw new RuntimeException("Deque should be empty after removing the only element");
        }
    }

    static void testRemoveLastOccurrencePresentMultiple() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        deque.add("c");
        // removeLastOccurrence("a") should remove the last "a" (the one added third)
        boolean removed = deque.removeLastOccurrence("a");
        if (!removed) {
            throw new RuntimeException("removeLastOccurrence should return true when element is present");
        }
        // After removal, deque should still contain one "a" (the first one)
        int countA = 0;
        for (String s : deque) {
            if ("a".equals(s)) countA++;
        }
        if (countA != 1) {
            throw new RuntimeException("After removeLastOccurrence, exactly one 'a' should remain");
        }
        // The order of elements should be [a, b, c]
        Object[] expected = {"a", "b", "c"};
        int i = 0;
        for (String s : deque) {
            if (!expected[i].equals(s)) {
                throw new RuntimeException("Deque elements out of expected order after removal");
            }
            i++;
        }
    }
}
