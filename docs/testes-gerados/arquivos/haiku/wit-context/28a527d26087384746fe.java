/*
 * @test
 * @summary Witup-generated checks for ArrayDeque descending iterator forEachRemaining
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        Iterator<String> it = deque.descendingIterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testVisitsElementsInDescendingOrder() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Iterator<Integer> it = deque.descendingIterator();
        List<Integer> seen = new ArrayList<>();
        Consumer<Integer> action = seen::add;
        it.forEachRemaining(action);
        check(seen.size() == 3, "expected 3 elements, got " + seen.size());
        check(seen.get(0) == 3 && seen.get(1) == 2 && seen.get(2) == 1,
              "unexpected order: " + seen);
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Iterator<String> it = deque.descendingIterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "action must not be called for empty deque");
        check(!it.hasNext(), "iterator must be exhausted");
    }

    static void testIteratorExhaustedAfterForEachRemaining() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");
        Iterator<String> it = deque.descendingIterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 2, "expected 2 elements, got " + seen.size());
        check(!it.hasNext(), "hasNext must be false after forEachRemaining");
        List<String> second = new ArrayList<>();
        it.forEachRemaining(second::add);
        check(second.isEmpty(), "second forEachRemaining must not visit elements");
    }

    static void testPartiallyConsumedIterator() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Iterator<Integer> it = deque.descendingIterator();
        int first = it.next();
        check(first == 3, "first descending element must be 3, got " + first);
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 2, "expected 2 remaining elements, got " + seen.size());
        check(seen.get(0) == 2 && seen.get(1) == 1, "unexpected remaining order: " + seen);
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsElementsInDescendingOrder();
        testEmptyDequeDoesNotInvokeAction();
        testIteratorExhaustedAfterForEachRemaining();
        testPartiallyConsumedIterator();
    }
}
