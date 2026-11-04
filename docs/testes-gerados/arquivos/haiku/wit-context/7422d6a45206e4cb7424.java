/*
 * @test
 * @summary Behavioral tests for ArrayDeque.forEach: null action, empty deque, ordering, wrap-around and concurrent modification
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyDequeDoesNotInvokeAction();
        testIterationOrderFirstAndLast();
        testWrapAroundOrder();
        testConcurrentModificationByAddLast();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        boolean thrown = false;
        try {
            deque.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        List<Integer> seen = new ArrayList<>();
        deque.forEach(x -> seen.add(x));
        check(seen.isEmpty(), "forEach on empty deque must not invoke action");
    }

    static void testIterationOrderFirstAndLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(1);
        deque.addLast(4);
        List<Integer> seen = new ArrayList<>();
        deque.forEach(x -> seen.add(x));
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        check(seen.equals(expected), "forEach order mismatch: " + seen);
    }

    static void testWrapAroundOrder() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= 10; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            deque.removeFirst();
        }
        for (int i = 11; i <= 15; i++) {
            deque.addLast(i);
        }
        List<Integer> seen = new ArrayList<>();
        deque.forEach(x -> seen.add(x));
        List<Integer> expected = new ArrayList<>();
        for (int i = 6; i <= 15; i++) {
            expected.add(i);
        }
        check(seen.equals(expected), "forEach wrap-around order mismatch: " + seen);
    }

    static void testConcurrentModificationByAddLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        boolean thrown = false;
        try {
            deque.forEach(x -> deque.addLast(99));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "forEach must throw ConcurrentModificationException when deque is structurally modified");
    }
}
