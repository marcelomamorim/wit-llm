/*
 * @test
 * @summary Witup-generated checks for LinkedBlockingDeque.offer
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testOfferNullThrowsNPE();
        testOfferAddsElementAtTail();
        testOfferReturnsFalseWhenFull();
    }

    static void testOfferNullThrowsNPE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.offer(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        if (!deque.isEmpty()) {
            throw new AssertionError("deque must remain empty after rejected null");
        }
    }

    static void testOfferAddsElementAtTail() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        if (!deque.offer("a")) {
            throw new AssertionError("offer(\"a\") should return true");
        }
        if (!deque.offer("b")) {
            throw new AssertionError("offer(\"b\") should return true");
        }
        if (deque.size() != 2) {
            throw new AssertionError("unexpected size: " + deque.size());
        }
        if (!"b".equals(deque.peekLast())) {
            throw new AssertionError("last element should be b");
        }
        if (!"a".equals(deque.peekFirst())) {
            throw new AssertionError("first element should be a");
        }
    }

    static void testOfferReturnsFalseWhenFull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        if (!deque.offer("a")) {
            throw new AssertionError("first offer should succeed");
        }
        if (deque.offer("b")) {
            throw new AssertionError("offer on full bounded deque should return false");
        }
        if (deque.size() != 1) {
            throw new AssertionError("unexpected size: " + deque.size());
        }
    }
}
