/*
 * @test
 * @summary Witup-derived tests for LinkedBlockingDeque spliterator forEachRemaining
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testIteratesRemainingInOrder();
        testEmptyDequeDoesNotInvokeAction();
        testSecondCallDoesNothing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        Spliterator<String> spliterator = deque.spliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testIteratesRemainingInOrder() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        Spliterator<String> spliterator = deque.spliterator();
        List<String> collected = new ArrayList<>();
        spliterator.forEachRemaining(collected::add);
        check(collected.size() == 3, "expected 3 elements but got " + collected.size());
        check("a".equals(collected.get(0)), "unexpected element at 0: " + collected.get(0));
        check("b".equals(collected.get(1)), "unexpected element at 1: " + collected.get(1));
        check("c".equals(collected.get(2)), "unexpected element at 2: " + collected.get(2));
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Spliterator<String> spliterator = deque.spliterator();
        List<String> collected = new ArrayList<>();
        spliterator.forEachRemaining(collected::add);
        check(collected.isEmpty(), "action must not be invoked for empty deque");
    }

    static void testSecondCallDoesNothing() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("p");
        deque.addLast("q");
        Spliterator<String> spliterator = deque.spliterator();
        List<String> first = new ArrayList<>();
        spliterator.forEachRemaining(first::add);
        check(first.size() == 2, "first traversal expected 2 elements but got " + first.size());
        List<String> second = new ArrayList<>();
        spliterator.forEachRemaining(second::add);
        check(second.isEmpty(), "second traversal must not revisit elements");
    }
}
