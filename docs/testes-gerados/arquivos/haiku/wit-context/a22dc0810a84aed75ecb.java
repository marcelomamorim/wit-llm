/*
 * @test
 * @summary Checks ArrayDeque spliterator forEachRemaining: NPE on null action, no action calls on empty deque, and in-order traversal across a wrapped circular layout
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyDequeDoesNotInvokeAction();
        testTraversalOrderAfterWraparound();
        testSpliteratorExhaustedAfterForEachRemaining();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        Spliterator<String> spliterator = deque.spliterator();
        boolean thrown = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        List<String> seen = new ArrayList<>();
        deque.spliterator().forEachRemaining(seen::add);
        check(seen.isEmpty(), "action must not be invoked for an empty deque");
    }

    static void testTraversalOrderAfterWraparound() {
        ArrayDeque<String> deque = new ArrayDeque<>(4);
        deque.addLast("c");
        deque.addFirst("b");
        deque.addFirst("a");
        deque.addLast("d");
        List<String> seen = new ArrayList<>();
        deque.spliterator().forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c", "d")),
              "traversal order must match deque order, got " + seen);

        deque.pollFirst();
        deque.addLast("e");
        seen.clear();
        deque.spliterator().forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c", "d", "e")),
              "traversal order must follow deque order after head removal, got " + seen);
    }

    static void testSpliteratorExhaustedAfterForEachRemaining() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("p");
        deque.add("q");
        Spliterator<String> spliterator = deque.spliterator();
        List<String> seen = new ArrayList<>();
        spliterator.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("p", "q")),
              "forEachRemaining must visit all elements, got " + seen);
        boolean advanced = spliterator.tryAdvance(seen::add);
        check(!advanced, "tryAdvance must return false after forEachRemaining consumed all elements");
        check(seen.size() == 2, "no extra element must be delivered after exhaustion");
    }
}
