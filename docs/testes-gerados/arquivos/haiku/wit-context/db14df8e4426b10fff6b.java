/*
 * @test
 * @summary WITUP generated tests for ConcurrentLinkedDeque.forEach
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionThrowsNpeOnNonEmptyDeque();
        testEmptyDequeDoesNotInvokeAction();
        testVisitsElementsInOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNpe() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.forEach(null);
            throw new AssertionError("expected NullPointerException for null action on empty deque");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullActionThrowsNpeOnNonEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        try {
            deque.forEach(null);
            throw new AssertionError("expected NullPointerException for null action on non-empty deque");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        List<String> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.isEmpty(), "action must not be invoked for empty deque");
    }

    static void testVisitsElementsInOrder() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.addLast("x");
        deque.addLast("y");
        deque.addFirst("w");
        List<String> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.size() == 3, "expected 3 elements but got " + seen.size());
        check(seen.get(0).equals("w"), "first element mismatch: " + seen.get(0));
        check(seen.get(1).equals("x"), "second element mismatch: " + seen.get(1));
        check(seen.get(2).equals("y"), "third element mismatch: " + seen.get(2));
    }
}
