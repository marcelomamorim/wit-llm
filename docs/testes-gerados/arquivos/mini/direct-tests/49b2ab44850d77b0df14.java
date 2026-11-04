/*
 * @test
 * @summary Test LinkedTransferQueue removeIf method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyQueue();
    }

    static void testRemoveIfWithNullPredicate() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        Predicate<String> filter = s -> s.length() == 3; // remove "one" and "two"
        boolean removed = queue.removeIf(filter);

        if (!removed) {
            throw new AssertionError("Expected removeIf to return true when elements removed");
        }

        if (queue.contains("one") || queue.contains("two")) {
            throw new AssertionError("Elements matching predicate were not removed");
        }

        if (!queue.contains("three")) {
            throw new AssertionError("Elements not matching predicate should remain");
        }
    }

    static void testRemoveIfReturnsFalseWhenNoMatch() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");

        Predicate<String> filter = s -> s.length() > 10; // no elements match
        boolean removed = queue.removeIf(filter);

        if (removed) {
            throw new AssertionError("Expected removeIf to return false when no elements removed");
        }

        if (!queue.contains("one") || !queue.contains("two")) {
            throw new AssertionError("No elements should have been removed");
        }
    }

    static void testRemoveIfOnEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        Predicate<String> filter = s -> true;
        boolean removed = queue.removeIf(filter);

        if (removed) {
            throw new AssertionError("Expected removeIf to return false on empty queue");
        }
    }
}
