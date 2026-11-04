/*
 * @test
 * @summary Behavioral tests for ConcurrentLinkedDeque.forEach(Consumer)
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testNullActionDoesNotVisitElements();
        testEmptyDequeInvokesNoAction();
        testVisitsElementsFromFirstToLast();
        testSkipsElementsRemovedBeforeTraversal();
        testCollectionConstructorElementsVisitedInOrder();
        testActionExceptionPropagates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        try {
            deque.forEach((Consumer<String>) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullActionDoesNotVisitElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        final int[] calls = {0};
        try {
            deque.forEach((Consumer<String>) null);
        } catch (NullPointerException expected) {
            // expected
        }
        check(calls[0] == 0, "no elements should be visited");
    }

    static void testEmptyDequeInvokesNoAction() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        final int[] calls = {0};
        deque.forEach(s -> calls[0]++);
        check(calls[0] == 0, "empty deque must not invoke action, got " + calls[0]);
    }

    static void testVisitsElementsFromFirstToLast() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        deque.addFirst("z");
        final List<String> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList("z", "a", "b", "c")),
              "unexpected order: " + seen);
    }

    static void testSkipsElementsRemovedBeforeTraversal() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        check("a".equals(deque.pollFirst()), "pollFirst should return a");
        check("c".equals(deque.pollLast()), "pollLast should return c");
        final List<String> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList("b")), "unexpected elements: " + seen);
    }

    static void testCollectionConstructorElementsVisitedInOrder() {
        ConcurrentLinkedDeque<Integer> deque =
            new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3));
        final List<Integer> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)), "unexpected elements: " + seen);
    }

    static void testActionExceptionPropagates() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        try {
            deque.forEach(s -> {
                throw new IllegalStateException("boom:" + s);
            });
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            check(expected.getMessage().equals("boom:a"),
                  "unexpected message: " + expected.getMessage());
        }
    }
}
