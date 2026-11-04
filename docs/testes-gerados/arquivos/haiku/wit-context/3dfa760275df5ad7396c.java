/*
 * @test
 * @summary WITUP-derived checks for LinkedBlockingDeque spliterator tryAdvance
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPEOnEmptyDeque();
        testNullActionThrowsNPEOnNonEmptyDeque();
        testEmptyDequeReturnsFalse();
        testAdvanceVisitsElementsInOrder();
        testAdvanceReturnsFalseAfterExhausted();
    }

    static void testNullActionThrowsNPEOnEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Spliterator<Integer> sp = deque.spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on empty deque");
    }

    static void testNullActionThrowsNPEOnNonEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        Spliterator<Integer> sp = deque.spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on non-empty deque");
        List<Integer> seen = new ArrayList<>();
        check(sp.tryAdvance(seen::add), "null action must not consume elements");
        check(seen.size() == 1 && seen.get(0) == 1, "first element should still be 1");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Spliterator<Integer> sp = deque.spliterator();
        List<Integer> seen = new ArrayList<>();
        boolean result = sp.tryAdvance(seen::add);
        check(!result, "tryAdvance on empty deque should return false");
        check(seen.isEmpty(), "action must not be invoked on empty deque");
    }

    static void testAdvanceVisitsElementsInOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Spliterator<Integer> sp = deque.spliterator();
        List<Integer> seen = new ArrayList<>();
        check(sp.tryAdvance(seen::add), "first call should return true");
        check(sp.tryAdvance(seen::add), "second call should return true");
        check(sp.tryAdvance(seen::add), "third call should return true");
        check(seen.size() == 3, "expected 3 elements, got " + seen.size());
        check(seen.get(0) == 1 && seen.get(1) == 2 && seen.get(2) == 3,
              "elements out of order: " + seen);
    }

    static void testAdvanceReturnsFalseAfterExhausted() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(7);
        Spliterator<Integer> sp = deque.spliterator();
        List<Integer> seen = new ArrayList<>();
        check(sp.tryAdvance(seen::add), "element should be delivered");
        check(!sp.tryAdvance(seen::add), "tryAdvance after exhaustion should return false");
        check(!sp.tryAdvance(seen::add), "repeated tryAdvance after exhaustion should return false");
        check(seen.size() == 1 && seen.get(0) == 7, "action should be invoked exactly once");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
