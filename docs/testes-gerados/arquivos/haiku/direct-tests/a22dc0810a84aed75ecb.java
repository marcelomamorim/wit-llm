/*
 * @test
 * @summary Verifies DeqIterator.forEachRemaining behavior via the public iterator() of ArrayDeque
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDequeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNpe() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        dq.addLast(1);
        Iterator<Integer> it = dq.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testNullActionOnEmptyDequeThrowsNpe() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        Iterator<Integer> it = dq.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on empty deque");
    }

    static void testEmptyDequeNoActions() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        Iterator<Integer> it = dq.iterator();
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(x -> out.add(x));
        check(out.isEmpty(), "action must not run for empty deque");
        check(!it.hasNext(), "iterator must be exhausted");
    }

    static void testVisitsAllElementsInOrder() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        List<Integer> out = new ArrayList<>();
        Iterator<Integer> it = dq.iterator();
        it.forEachRemaining(x -> out.add(x));
        check(out.equals(java.util.Arrays.asList(1, 2, 3)),
              "expected [1, 2, 3] but got " + out);
        check(!it.hasNext(), "iterator must be exhausted after forEachRemaining");
    }

    static void testVisitsOnlyRemainingAfterNext() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        Iterator<Integer> it = dq.iterator();
        check(it.next() == 1, "first next() should be 1");
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(x -> out.add(x));
        check(out.equals(java.util.Arrays.asList(2, 3)),
              "expected remaining [2, 3] but got " + out);
    }

    static void testWrappedCircularLayout() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 1; i <= 10; i++) {
            dq.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            dq.pollFirst();
        }
        for (int i = 11; i <= 15; i++) {
            dq.addLast(i);
        }
        List<Integer> out = new ArrayList<>();
        dq.iterator().forEachRemaining(x -> out.add(x));
        List<Integer> expected = java.util.Arrays.asList(6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        check(out.equals(expected), "expected " + expected + " but got " + out);
    }

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionOnEmptyDequeThrowsNpe();
        testEmptyDequeNoActions();
        testVisitsAllElementsInOrder();
        testVisitsOnlyRemainingAfterNext();
        testWrappedCircularLayout();
    }
}
