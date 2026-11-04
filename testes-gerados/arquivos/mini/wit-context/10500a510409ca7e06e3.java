/*
 * @test
 * @summary Test deterministic behavior of ArrayDeque.offerLast(E) including null rejection
 * @run main java.util.ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testOfferLastWithNonNull();
        testOfferLastWithNull();
    }

    private static void testOfferLastWithNonNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.offerLast("test");
        if (!result) {
            throw new AssertionError("offerLast should return true when adding non-null element");
        }
        String polled = deque.pollLast();
        if (!"test".equals(polled)) {
            throw new AssertionError("Element added by offerLast not found at the end of deque");
        }
    }

    private static void testOfferLastWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.offerLast(null);
            throw new AssertionError("offerLast should throw NullPointerException when adding null");
        } catch (NullPointerException expected) {
            // Expected exception, test passes
        }
    }
}
