/*
 * @test
 * @summary Deterministic tests for LinkedBlockingDeque.offerLast(E) method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testOfferLastNull();
        testOfferLastNonNull();
    }

    private static void testOfferLastNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.offerLast(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testOfferLastNonNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean offered = deque.offerLast("test");
        if (!offered) {
            throw new Error("offerLast returned false unexpectedly");
        }
        String polled = deque.pollLast();
        if (!"test".equals(polled)) {
            throw new Error("Deque content mismatch after offerLast");
        }
    }
}
