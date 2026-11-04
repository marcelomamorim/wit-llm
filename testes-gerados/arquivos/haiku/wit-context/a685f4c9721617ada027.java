/*
 * @test
 * @summary WITUP-generated behavioral tests for ConcurrentLinkedQueue.removeIf
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNullPointerException();
        testRemovesMatchingElementsPreservingOrder();
        testReturnsFalseWhenNoElementMatches();
        testEmptyQueueReturnsFalse();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFilterThrowsNullPointerException() {
        ConcurrentLinkedQueue<String> queue =
                new ConcurrentLinkedQueue<>(Arrays.asList("a", "bb"));
        boolean thrown = false;
        try {
            queue.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(queue.toString().equals("[a, bb]"),
                "queue must not change when filter is null");
    }

    static void testRemovesMatchingElementsPreservingOrder() {
        ConcurrentLinkedQueue<String> queue =
                new ConcurrentLinkedQueue<>(Arrays.asList("a", "bb", "c", "dd"));
        boolean changed = queue.removeIf(s -> s.length() == 2);
        check(changed, "removeIf must return true when elements are removed");
        check(queue.toString().equals("[a, c]"),
                "remaining elements must be [a, c] but was " + queue);
    }

    static void testReturnsFalseWhenNoElementMatches() {
        ConcurrentLinkedQueue<String> queue =
                new ConcurrentLinkedQueue<>(Arrays.asList("a", "b"));
        boolean changed = queue.removeIf(s -> false);
        check(!changed, "removeIf must return false when nothing matches");
        check(queue.toString().equals("[a, b]"),
                "queue must be unchanged when nothing matches");
    }

    static void testEmptyQueueReturnsFalse() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        boolean changed = queue.removeIf(s -> true);
        check(!changed, "removeIf on empty queue must return false");
        check(queue.isEmpty(), "empty queue must remain empty");
    }
}
