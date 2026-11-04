/*
 * @test
 * @summary Witup-generated tests for ConcurrentLinkedDeque.retainAll
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testRetainsOnlyContainedElements();
        testReturnsFalseWhenNothingRemoved();
        testEmptyDequeReturnsFalse();
        testEmptyCollectionClearsDeque();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<String>(Arrays.asList("a", "b"));
        boolean thrown = false;
        try {
            deque.retainAll(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "retainAll(null) must throw NullPointerException");
        check(deque.size() == 2, "deque must be unchanged after failed retainAll(null)");
    }

    static void testRetainsOnlyContainedElements() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<String>(Arrays.asList("a", "b", "c"));
        boolean changed = deque.retainAll(Arrays.asList("b", "c", "z"));
        check(changed, "retainAll must return true when an element is removed");
        check(deque.size() == 2, "size must be 2 after retaining b and c");
        check(!deque.contains("a"), "a must be removed");
        check(deque.contains("b"), "b must be retained");
        check(deque.contains("c"), "c must be retained");
    }

    static void testReturnsFalseWhenNothingRemoved() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<String>(Arrays.asList("a", "b"));
        boolean changed = deque.retainAll(Arrays.asList("a", "b", "c"));
        check(!changed, "retainAll must return false when nothing is removed");
        check(deque.size() == 2, "size must remain 2");
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<String>();
        boolean changed = deque.retainAll(Arrays.asList("a"));
        check(!changed, "retainAll on empty deque must return false");
        check(deque.isEmpty(), "deque must remain empty");
    }

    static void testEmptyCollectionClearsDeque() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<String>(Arrays.asList("a", "b"));
        boolean changed = deque.retainAll(Collections.<String>emptyList());
        check(changed, "retainAll with empty collection must return true");
        check(deque.isEmpty(), "deque must be empty after retaining nothing");
    }
}
