/*
 * @test
 * @summary Test deterministic behaviors of java.util.ArrayDeque.removeIf(Predicate)
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Predicate;
import java.util.Objects;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfNormalPredicate();
    }

    private static void testRemoveIfNullPredicate() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        try {
            deque.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveIfNormalPredicate() {
        ArrayDeque<String> deque = new ArrayDeque<>();
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
            throw new RuntimeException("Expected removeIf to remove element 'two'");
        }

        if (deque.contains("two")) {
            throw new RuntimeException("Element 'two' should have been removed");
        }

        // Verify other elements remain
        if (!deque.contains("one") || !deque.contains("three")) {
            throw new RuntimeException("Other elements should remain after removeIf");
        }
    }
}
