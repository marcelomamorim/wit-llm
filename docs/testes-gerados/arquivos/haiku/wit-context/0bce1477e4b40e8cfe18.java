/*
 * @test
 * @summary Witup-generated tests for LinkedBlockingDeque.offerFirst
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullRejected();
        testCapacityBound();
        testOrdering();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullRejected() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        check(thrown, "offerFirst(null) must throw NullPointerException");
        check(deque.isEmpty(), "deque must remain empty after rejected null");
        check(deque.size() == 0, "size must be 0 after rejected null");
    }

    static void testCapacityBound() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.offerFirst("a"), "first offerFirst must succeed");
        check(!deque.offerFirst("b"), "offerFirst on full deque must return false");
        check(deque.size() == 1, "size must stay at capacity");
        check("a".equals(deque.peekFirst()), "head must remain the first element");
    }

    static void testOrdering() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.offerFirst("a"), "offerFirst a must succeed");
        check(deque.offerFirst("b"), "offerFirst b must succeed");
        check("b".equals(deque.peekFirst()), "last offerFirst must be head");
        check("b".equals(deque.pollFirst()), "pollFirst must return b");
        check("a".equals(deque.pollFirst()), "pollFirst must return a");
        check(deque.pollFirst() == null, "empty deque pollFirst must return null");
    }
}
