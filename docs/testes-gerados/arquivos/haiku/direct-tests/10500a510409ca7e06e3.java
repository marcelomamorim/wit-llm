/*
 * @test
 * @summary Behavioral tests for ArrayDeque.offerLast(E e)
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Arrays;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeReturnsTrueAndAppends();
        testOfferLastPreservesInsertionOrder();
        testOfferLastDoesNotChangeHead();
        testGrowsBeyondInitialCapacity();
        testOfferLastOnCollectionConstructedDeque();
        testNullElementIsRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyDequeReturnsTrueAndAppends() {
        ArrayDeque<String> d = new ArrayDeque<>();
        boolean result = d.offerLast("a");
        check(result, "offerLast must return true");
        check(d.size() == 1, "size must be 1 after one offerLast, was " + d.size());
        check("a".equals(d.peekLast()), "peekLast must return the offered element");
        check("a".equals(d.peekFirst()), "peekFirst must return the only element");
    }

    static void testOfferLastPreservesInsertionOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < 5; i++) {
            check(d.offerLast(i), "offerLast must return true for " + i);
        }
        for (int i = 0; i < 5; i++) {
            Integer polled = d.pollFirst();
            check(polled != null && polled == i, "expected " + i + " but got " + polled);
        }
        check(d.isEmpty(), "deque must be empty after polling all elements");
    }

    static void testOfferLastDoesNotChangeHead() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.offerLast("first");
        d.offerLast("second");
        check("first".equals(d.peekFirst()), "head must remain 'first'");
        check("second".equals(d.peekLast()), "tail must be 'second'");
        check(d.size() == 2, "size must be 2, was " + d.size());
    }

    static void testGrowsBeyondInitialCapacity() {
        ArrayDeque<Integer> d = new ArrayDeque<>(1);
        int n = 100;
        for (int i = 0; i < n; i++) {
            check(d.offerLast(i), "offerLast must return true at " + i);
        }
        check(d.size() == n, "size must be " + n + ", was " + d.size());
        check(d.peekLast() == n - 1, "last element must be " + (n - 1));
        check(d.peekFirst() == 0, "first element must be 0");
    }

    static void testOfferLastOnCollectionConstructedDeque() {
        ArrayDeque<String> d = new ArrayDeque<>(Arrays.asList("x", "y"));
        check(d.offerLast("z"), "offerLast must return true");
        check(d.size() == 3, "size must be 3, was " + d.size());
        check("x".equals(d.pollFirst()), "first must be x");
        check("y".equals(d.pollFirst()), "second must be y");
        check("z".equals(d.pollFirst()), "third must be z");
    }

    static void testNullElementIsRejected() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.offerLast("keep");
        try {
            d.offerLast(null);
            throw new RuntimeException("offerLast(null) must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(d.size() == 1, "failed offer must not change size, was " + d.size());
        check("keep".equals(d.peekLast()), "tail must remain unchanged after failed offer");
    }
}
