/*
 * @test
 * @summary Tests ArrayDeque spliterator tryAdvance: null action, empty deque, FIFO traversal and late binding
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyDequeReturnsFalseWithoutCallingAction();
        testAdvancesInFifoOrder();
        testSingleElement();
        testLateBindingSeesElementsAddedBeforeFirstAdvance();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        Spliterator<String> spliterator = deque.spliterator();
        boolean thrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testEmptyDequeReturnsFalseWithoutCallingAction() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Spliterator<String> spliterator = deque.spliterator();
        boolean[] called = new boolean[1];
        boolean result = spliterator.tryAdvance(x -> called[0] = true);
        check(!result, "tryAdvance on empty deque must return false");
        check(!called[0], "action must not be invoked on empty deque");
    }

    static void testAdvancesInFifoOrder() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        Spliterator<String> spliterator = deque.spliterator();
        List<String> seen = new ArrayList<>();
        while (spliterator.tryAdvance(x -> seen.add(x))) {
            // consume all elements
        }
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "elements must be visited in FIFO order, got " + seen);
        check(!spliterator.tryAdvance(x -> seen.add(x)),
              "tryAdvance must return false after exhaustion");
        check(seen.size() == 3, "no extra elements must be delivered after exhaustion");
    }

    static void testSingleElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        Spliterator<String> spliterator = deque.spliterator();
        List<String> seen = new ArrayList<>();
        check(spliterator.tryAdvance(x -> seen.add(x)), "first tryAdvance must return true");
        check(seen.equals(Arrays.asList("x")), "single element must be delivered once");
        check(!spliterator.tryAdvance(x -> seen.add(x)), "second tryAdvance must return false");
    }

    static void testLateBindingSeesElementsAddedBeforeFirstAdvance() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Spliterator<String> spliterator = deque.spliterator();
        deque.add("late");
        List<String> seen = new ArrayList<>();
        check(spliterator.tryAdvance(x -> seen.add(x)),
              "tryAdvance must observe element added before first advance");
        check(seen.equals(Arrays.asList("late")), "late-bound element must be delivered, got " + seen);
    }
}
