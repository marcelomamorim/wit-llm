/*
 * @test
 * @summary Test LinkedBlockingDeque.offerLast(E) method behavior
 * @run main LinkedBlockingDequeWitupTest_P7fd362ff4cd5
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_P7fd362ff4cd5 {
    public static void main(String[] args) {
        testOfferLastWithNonNull();
        testOfferLastWithNull();
    }

    static void testOfferLastWithNonNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerLast("test");
        if (!result) {
            throw new AssertionError("offerLast should return true when adding non-null element");
        }
        String polled = deque.pollLast();
        if (!"test".equals(polled)) {
            throw new AssertionError("Element added by offerLast not found at tail");
        }
    }

    static void testOfferLastWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.offerLast(null);
            throw new AssertionError("offerLast(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
