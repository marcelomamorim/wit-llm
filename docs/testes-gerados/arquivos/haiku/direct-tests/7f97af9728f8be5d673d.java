/*
 * @test
 * @summary Witup-style tests for PriorityBlockingQueue.offer
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferReturnsTrueAndIncrementsSize();
        testOfferNullThrowsNullPointerException();
        testOfferNonComparableWithNaturalOrderingThrowsClassCastException();
        testOfferNaturalOrderingPeekReturnsMinimum();
        testOfferWithComparatorPeekReturnsMaximum();
        testOfferManyElementsGrowsAndKeepsOrder();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOfferReturnsTrueAndIncrementsSize() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.offer(7), "offer should return true");
        check(q.size() == 1, "size should be 1 after one offer, was " + q.size());
        check(q.offer(3), "second offer should return true");
        check(q.size() == 2, "size should be 2 after two offers, was " + q.size());
    }

    static void testOfferNullThrowsNullPointerException() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        try {
            q.offer(null);
            throw new RuntimeException("offer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 0, "queue should remain empty after rejected null");
    }

    static void testOfferNonComparableWithNaturalOrderingThrowsClassCastException() {
        PriorityBlockingQueue<Object> q = new PriorityBlockingQueue<>();
        try {
            q.offer(new Object());
            throw new RuntimeException("non-comparable offer should throw ClassCastException");
        } catch (ClassCastException expected) {
            // expected
        }
    }

    static void testOfferNaturalOrderingPeekReturnsMinimum() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(7);
        q.offer(1);
        q.offer(5);
        check(q.peek() == 1, "peek should return minimum 1, was " + q.peek());
        check(q.size() == 3, "size should be 3, was " + q.size());
    }

    static void testOfferWithComparatorPeekReturnsMaximum() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(11, Comparator.<Integer>reverseOrder());
        q.offer(1);
        q.offer(5);
        q.offer(3);
        check(q.peek() == 5, "peek with reverse comparator should return 5, was " + q.peek());
    }

    static void testOfferManyElementsGrowsAndKeepsOrder() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(1);
        int n = 1000;
        for (int i = n - 1; i >= 0; i--) {
            check(q.offer(i), "offer should return true for " + i);
        }
        check(q.size() == n, "size should be " + n + ", was " + q.size());
        for (int i = 0; i < n; i++) {
            Integer v = q.poll();
            check(v != null && v == i, "poll order mismatch at " + i + ", got " + v);
        }
        check(q.poll() == null, "queue should be empty after draining");
    }
}
