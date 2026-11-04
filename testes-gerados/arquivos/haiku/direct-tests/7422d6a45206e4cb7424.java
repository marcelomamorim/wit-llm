/*
 * @test
 * @summary Behavioral tests for ArrayDeque.forEach: ordering, wrap-around, null action, and concurrent modification detection
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeDoesNotInvokeAction();
        testIterationOrderIsHeadToTail();
        testIterationAfterWrapAround();
        testNullActionThrowsNullPointerException();
        testModificationDuringForEachThrowsConcurrentModificationException();
        testForEachDoesNotRemoveElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        List<Integer> seen = new ArrayList<>();
        d.forEach(seen::add);
        check(seen.isEmpty(), "action must not be called for empty deque");
    }

    static void testIterationOrderIsHeadToTail() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        d.addFirst(0);
        List<Integer> seen = new ArrayList<>();
        d.forEach(seen::add);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        check(seen.equals(expected), "forEach order must be head to tail");
    }

    static void testIterationAfterWrapAround() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            d.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            d.pollFirst();
        }
        for (int i = 10; i <= 20; i++) {
            d.addLast(i);
        }
        List<Integer> seen = new ArrayList<>();
        d.forEach(seen::add);
        List<Integer> expected = new ArrayList<>();
        for (int i = 5; i <= 20; i++) {
            expected.add(i);
        }
        check(seen.equals(expected), "forEach must traverse wrapped elements in order");
    }

    static void testNullActionThrowsNullPointerException() {
        ArrayDeque<Integer> empty = new ArrayDeque<>();
        try {
            empty.forEach(null);
            throw new RuntimeException("expected NullPointerException for empty deque");
        } catch (NullPointerException expected) {
        }
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(1);
        try {
            d.forEach(null);
            throw new RuntimeException("expected NullPointerException for non-empty deque");
        } catch (NullPointerException expected) {
        }
    }

    static void testModificationDuringForEachThrowsConcurrentModificationException() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        List<Integer> seen = new ArrayList<>();
        try {
            d.forEach(x -> {
                seen.add(x);
                if (x == 1) {
                    d.addLast(4);
                }
            });
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
        List<Integer> expectedSeen = new ArrayList<>();
        expectedSeen.add(1);
        expectedSeen.add(2);
        expectedSeen.add(3);
        check(seen.equals(expectedSeen), "elements present at start must be visited before CME");
    }

    static void testForEachDoesNotRemoveElements() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(7);
        d.addLast(8);
        d.forEach(x -> { });
        check(d.size() == 2, "forEach must not change size");
        check(d.peekFirst() == 7 && d.peekLast() == 8, "forEach must not change contents");
    }
}
