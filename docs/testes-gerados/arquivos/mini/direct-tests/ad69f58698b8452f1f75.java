/*
 * @test
 * @summary Test ConcurrentLinkedDeque removeIf method behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoElementsRemoved();
        testRemoveIfAllElementsRemoved();
        testRemoveIfOnEmptyDeque();
    }

    static void testRemoveIfWithNullPredicate() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("keep");
        deque.add("remove");
        deque.add("keep");
        deque.add("remove");

        Predicate<String> filter = s -> s.equals("remove");
        boolean changed = deque.removeIf(filter);

        if (!changed) {
            throw new AssertionError("Expected removeIf to return true when elements removed");
        }

        for (String s : deque) {
            if (s.equals("remove")) {
                throw new AssertionError("Element 'remove' should have been removed");
            }
        }

        if (deque.size() != 2) {
            throw new AssertionError("Expected size 2 after removals, got " + deque.size());
        }
    }

    static void testRemoveIfNoElementsRemoved() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");

        Predicate<String> filter = s -> s.equals("none");
        boolean changed = deque.removeIf(filter);

        if (changed) {
            throw new AssertionError("Expected removeIf to return false when no elements removed");
        }

        if (deque.size() != 2) {
            throw new AssertionError("Expected size 2 when no elements removed, got " + deque.size());
        }
    }

    static void testRemoveIfAllElementsRemoved() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("remove1");
        deque.add("remove2");

        Predicate<String> filter = s -> s.startsWith("remove");
        boolean changed = deque.removeIf(filter);

        if (!changed) {
            throw new AssertionError("Expected removeIf to return true when all elements removed");
        }

        if (!deque.isEmpty()) {
            throw new AssertionError("Expected deque to be empty after removing all elements");
        }
    }

    static void testRemoveIfOnEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Predicate<String> filter = s -> true;
        boolean changed = deque.removeIf(filter);

        if (changed) {
            throw new AssertionError("Expected removeIf to return false on empty deque");
        }

        if (!deque.isEmpty()) {
            throw new AssertionError("Expected deque to remain empty");
        }
    }
}
