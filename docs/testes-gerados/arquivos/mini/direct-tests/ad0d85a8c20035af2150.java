/*
 * @test
 * @summary Test PriorityBlockingQueue.removeIf(Predicate) behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Predicate;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfRejectsNullPredicate();
    }

    static void testRemoveIfRemovesMatchingElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        // Remove even numbers
        boolean removed = queue.removeIf(new Predicate<Integer>() {
            public boolean test(Integer i) {
                return i % 2 == 0;
            }
        });
        if (!removed) {
            throw new AssertionError("Expected removeIf to return true when elements removed");
        }
        // After removal, queue should not contain 2
        if (queue.contains(2)) {
            throw new AssertionError("Queue should not contain removed element 2");
        }
        // Queue should still contain 1 and 3
        if (!queue.contains(1) || !queue.contains(3)) {
            throw new AssertionError("Queue should still contain elements 1 and 3");
        }
    }

    static void testRemoveIfReturnsFalseWhenNoMatch() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        // Predicate that matches nothing
        boolean removed = queue.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        });
        if (removed) {
            throw new AssertionError("Expected removeIf to return false when no elements removed");
        }
        // Queue should remain unchanged
        if (!queue.contains("a") || !queue.contains("b")) {
            throw new AssertionError("Queue should remain unchanged when no elements removed");
        }
    }

    static void testRemoveIfRejectsNullPredicate() {
        PriorityBlockingQueue<Object> queue = new PriorityBlockingQueue<>();
        try {
            queue.removeIf(null);
            throw new AssertionError("Expected NullPointerException when passing null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
