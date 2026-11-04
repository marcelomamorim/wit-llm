/*
 * @test
 * @summary Tests LinkedBlockingDeque iterator forEachRemaining observable behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testConsumesAllElementsInOrder();
        testEmptyDequeDoesNothing();
        testConsumesOnlyRemainingElements();
        testSecondCallDoesNotRevisit();
        testNullActionThrowsNpeAndKeepsIteratorUsable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testConsumesAllElementsInOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Iterator<Integer> it = deque.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "expected [1, 2, 3] but got " + seen);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testEmptyDequeDoesNothing() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(10);
        Iterator<String> it = deque.iterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "expected no elements but got " + seen);
        check(!it.hasNext(), "iterator should have no next element");
    }

    static void testConsumesOnlyRemainingElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Iterator<Integer> it = deque.iterator();
        check(it.next() == 1, "first element should be 1");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(2, 3)),
              "expected [2, 3] but got " + seen);
    }

    static void testSecondCallDoesNotRevisit() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(10);
        deque.add(20);
        Iterator<Integer> it = deque.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(10, 20)),
              "expected [10, 20] exactly once but got " + seen);
    }

    static void testNullActionThrowsNpeAndKeepsIteratorUsable() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(7);
        deque.add(8);
        Iterator<Integer> it = deque.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
        check(it.hasNext(), "iterator should still have elements after NPE");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(7, 8)),
              "expected [7, 8] after failed call but got " + seen);
    }
}
