/*
 * @test
 * @summary Checks forEachRemaining on LinkedBlockingDeque iterators: empty deque, ordered traversal, partial traversal after next(), batched traversal over more than 64 elements, null action, and exhausted iterator.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeDoesNotInvokeAction();
        testAllElementsInOrder();
        testRemainingElementsAfterNext();
        testLargeDequeBatches();
        testNullActionThrowsNpe();
        testExhaustedIteratorInvokesNothing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static LinkedBlockingDeque<Integer> filled(int n) {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        for (int i = 0; i < n; i++) {
            d.add(i);
        }
        return d;
    }

    static List<Integer> drain(Iterator<Integer> it) {
        List<Integer> out = new ArrayList<>();
        it.forEachRemaining(out::add);
        return out;
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        Iterator<Integer> it = new LinkedBlockingDeque<Integer>().iterator();
        List<Integer> out = drain(it);
        check(out.isEmpty(), "expected no elements, got " + out);
        check(!it.hasNext(), "iterator should have no next element");
    }

    static void testAllElementsInOrder() {
        Iterator<Integer> it = filled(5).iterator();
        List<Integer> out = drain(it);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expected.add(i);
        }
        check(out.equals(expected), "expected " + expected + " but got " + out);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testRemainingElementsAfterNext() {
        Iterator<Integer> it = filled(5).iterator();
        check(it.next() == 0, "first element should be 0");
        List<Integer> out = drain(it);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        check(out.equals(expected), "expected " + expected + " but got " + out);
    }

    static void testLargeDequeBatches() {
        int n = 200;
        Iterator<Integer> it = filled(n).iterator();
        List<Integer> out = drain(it);
        check(out.size() == n, "expected " + n + " elements but got " + out.size());
        for (int i = 0; i < n; i++) {
            check(out.get(i) == i, "wrong element at index " + i + ": " + out.get(i));
        }
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNullActionThrowsNpe() {
        Iterator<Integer> it = filled(3).iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
    }

    static void testExhaustedIteratorInvokesNothing() {
        Iterator<Integer> it = filled(3).iterator();
        drain(it);
        final int[] calls = {0};
        it.forEachRemaining(x -> calls[0]++);
        check(calls[0] == 0, "action should not be invoked on exhausted iterator");
    }
}
