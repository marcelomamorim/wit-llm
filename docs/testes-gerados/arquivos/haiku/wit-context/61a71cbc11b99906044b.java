/*
 * @test
 * @summary Verifies ArrayDeque.offer appends at the tail, returns true, and rejects null elements with NullPointerException without modifying the deque
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferReturnsTrue();
        testOfferAppendsAtTail();
        testOfferNullThrowsNPE();
        testOfferNullLeavesDequeUnchanged();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOfferReturnsTrue() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        check(deque.offer("a") == true, "offer should return true for a non-null element");
        check(deque.size() == 1, "size should be 1 after one offer");
    }

    static void testOfferAppendsAtTail() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.offer("a");
        deque.offer("b");
        deque.offer("c");
        check("a".equals(deque.peekFirst()), "first element should be the earliest offered");
        check("c".equals(deque.peekLast()), "last element should be the most recently offered");
        check(deque.size() == 3, "size should be 3 after three offers");
        check("a".equals(deque.pollFirst()), "pollFirst should return elements in offer order");
        check("b".equals(deque.pollFirst()), "pollFirst should return elements in offer order");
        check("c".equals(deque.pollFirst()), "pollFirst should return elements in offer order");
        check(deque.isEmpty(), "deque should be empty after polling all elements");
    }

    static void testOfferNullThrowsNPE() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean thrown = false;
        try {
            deque.offer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "offer(null) should throw NullPointerException");
    }

    static void testOfferNullLeavesDequeUnchanged() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.offer("x");
        boolean thrown = false;
        try {
            deque.offer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "offer(null) should throw NullPointerException");
        check(deque.size() == 1, "failed offer(null) should not change size");
        check("x".equals(deque.peekLast()), "failed offer(null) should not change the tail element");
    }
}
