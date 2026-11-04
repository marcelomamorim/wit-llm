/*
 * @test
 * @summary Witup-style tests for LinkedBlockingDeque.offerFirst
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullThrowsNPE();
        testEmptyDequeReturnsTrue();
        testInsertsAtHead();
        testCapacityFullReturnsFalse();
        testNullDoesNotModifyDeque();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNPE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "offerFirst(null) must throw NullPointerException");
    }

    static void testEmptyDequeReturnsTrue() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.offerFirst("a"), "offerFirst on empty unbounded deque must return true");
        check("a".equals(deque.peekFirst()), "element must be first after offerFirst");
        check(deque.size() == 1, "size must be 1 after one insertion");
    }

    static void testInsertsAtHead() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerFirst("a");
        deque.offerFirst("b");
        check("b".equals(deque.peekFirst()), "last offerFirst element must be at head");
        check("a".equals(deque.peekLast()), "earlier element must be at tail");
        check(deque.size() == 2, "size must be 2");
    }

    static void testCapacityFullReturnsFalse() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.offerFirst("a"), "first offerFirst within capacity must return true");
        check(!deque.offerFirst("b"), "offerFirst on full deque must return false");
        check(deque.size() == 1, "size must remain 1 when insertion is refused");
        check("a".equals(deque.peekFirst()), "original element must remain at head");
    }

    static void testNullDoesNotModifyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerFirst("a");
        try {
            deque.offerFirst(null);
        } catch (NullPointerException e) {
            // expected
        }
        check(deque.size() == 1, "failed null insertion must not change size");
        check("a".equals(deque.peekFirst()), "failed null insertion must not change head");
    }
}
