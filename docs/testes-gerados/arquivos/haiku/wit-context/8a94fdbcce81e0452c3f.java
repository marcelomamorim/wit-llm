/*
 * @test
 * @summary Witup-derived checks for LinkedBlockingDeque.offerLast null rejection, appending and capacity behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRejected();
        testOfferLastAppends();
        testOfferLastRespectsCapacity();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullRejected() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "offerLast(null) must throw NullPointerException");
        check(deque.size() == 0, "deque must remain empty after rejected null");
    }

    static void testOfferLastAppends() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.offerLast("a"), "offerLast on unbounded deque must return true");
        check(deque.offerLast("b"), "second offerLast must return true");
        check(deque.size() == 2, "size must be 2");
        check("b".equals(deque.peekLast()), "last element must be the last offered");
        check("b".equals(deque.pollLast()), "pollLast must return last offered element");
        check("a".equals(deque.pollLast()), "pollLast must return previous element");
        check(deque.pollLast() == null, "pollLast on empty deque must return null");
    }

    static void testOfferLastRespectsCapacity() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.offerLast("x"), "offerLast within capacity must return true");
        check(deque.remainingCapacity() == 0, "remainingCapacity must be 0 when full");
        check(!deque.offerLast("y"), "offerLast on full deque must return false");
        check(deque.size() == 1, "size must remain 1 after rejected offer");
        check("x".equals(deque.peekLast()), "existing element must be unchanged");
    }
}
