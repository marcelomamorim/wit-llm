/*
 * @test
 * @summary Verifies observable behavior of LinkedBlockingDeque.offerLast: null rejection, FIFO append order, and capacity-bound refusal.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testOfferLastNullThrowsNPE();
        testOfferLastNullOnFullDequeThrowsNPE();
        testOfferLastAppendsAtTail();
        testOfferLastReturnsFalseWhenFull();
        testOfferLastUnboundedReturnsTrue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOfferLastNullThrowsNPE() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        try {
            d.offerLast(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(d.isEmpty(), "deque must remain empty after null offer");
    }

    static void testOfferLastNullOnFullDequeThrowsNPE() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(1);
        check(d.offerLast(1), "first offer should succeed");
        try {
            d.offerLast(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(d.size() == 1, "size must be unchanged after null offer");
    }

    static void testOfferLastAppendsAtTail() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        check(d.offerLast(1), "offerLast(1) should return true");
        check(d.offerLast(2), "offerLast(2) should return true");
        check(d.offerLast(3), "offerLast(3) should return true");
        check(d.size() == 3, "size should be 3");
        check(d.peekLast() == 3, "last element should be 3");
        check(d.peekFirst() == 1, "first element should be 1");
        check(d.pollFirst() == 1, "pollFirst should return 1");
        check(d.pollFirst() == 2, "pollFirst should return 2");
        check(d.pollFirst() == 3, "pollFirst should return 3");
        check(d.isEmpty(), "deque should be empty");
    }

    static void testOfferLastReturnsFalseWhenFull() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(1);
        check(d.offerLast(1), "first offer should return true");
        check(!d.offerLast(2), "offer beyond capacity should return false");
        check(d.size() == 1, "size should remain 1");
        check(d.remainingCapacity() == 0, "remainingCapacity should be 0");
        check(d.peekLast() == 1, "rejected element must not be stored");
    }

    static void testOfferLastUnboundedReturnsTrue() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        for (int i = 0; i < 100; i++) {
            check(d.offerLast(i), "unbounded offerLast should return true");
        }
        check(d.size() == 100, "size should be 100");
    }
}
