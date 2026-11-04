/*
 * @test
 * @summary Test ConcurrentLinkedDeque.removeIf(Predicate) for null filter and basic removal
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullFilter();
        testRemoveIfBasicRemoval();
    }

    private static void testRemoveIfNullFilter() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveIfBasicRemoval() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        Predicate<String> removeTwo = new Predicate<String>() {
            public boolean test(String s) {
                return "two".equals(s);
            }
        };

        boolean removed = deque.removeIf(removeTwo);
        if (!removed) {
            throw new RuntimeException("Expected removeIf to remove element and return true");
        }

        // Verify "two" is removed
        for (String s : deque) {
            if ("two".equals(s)) {
                throw new RuntimeException("Element 'two' was not removed");
            }
        }

        // Verify other elements remain
        if (!deque.contains("one") || !deque.contains("three")) {
            throw new RuntimeException("Other elements were incorrectly removed");
        }

        // Test removeIf with predicate that matches no elements
        Predicate<String> removeNone = new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        };

        boolean removedNone = deque.removeIf(removeNone);
        if (removedNone) {
            throw new RuntimeException("Expected removeIf to return false when no elements removed");
        }

        // Test removeIf with predicate that matches all elements
        Predicate<String> removeAll = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };

        boolean removedAll = deque.removeIf(removeAll);
        if (!removedAll) {
            throw new RuntimeException("Expected removeIf to return true when elements removed");
        }

        if (!deque.isEmpty()) {
            throw new RuntimeException("Expected deque to be empty after removing all elements");
        }
    }
}
