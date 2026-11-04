/*
 * @test
 * @summary WITUP-derived tests for ConcurrentLinkedDeque.removeIf
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyDequeReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFilterThrowsNPE() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        try {
            deque.removeIf(null);
        } catch (NullPointerException e) {
            check(deque.size() == 2, "removeIf(null) must not modify the deque");
            check(deque.contains(1) && deque.contains(2),
                  "removeIf(null) must preserve elements");
            return;
        }
        throw new RuntimeException("removeIf(null) must throw NullPointerException");
    }

    static void testRemovesMatchingElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        deque.add(4);
        Predicate<Integer> even = x -> x % 2 == 0;
        boolean changed = deque.removeIf(even);
        check(changed, "removeIf must return true when elements are removed");
        check(deque.size() == 2, "two even elements must be removed");
        check(deque.contains(1) && deque.contains(3), "odd elements must remain");
        check(!deque.contains(2) && !deque.contains(4), "even elements must be gone");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(3);
        Predicate<Integer> even = x -> x % 2 == 0;
        boolean changed = deque.removeIf(even);
        check(!changed, "removeIf must return false when nothing matches");
        check(deque.size() == 2, "deque must be unchanged when nothing matches");
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        Predicate<Integer> all = x -> true;
        boolean changed = deque.removeIf(all);
        check(!changed, "removeIf on empty deque must return false");
        check(deque.isEmpty(), "empty deque must remain empty");
    }
}
