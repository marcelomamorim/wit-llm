/*
 * @test
 * @summary Test deterministic behavior of ArrayDeque.offerFirst(E) including null rejection
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testOfferFirstWithNonNull();
        testOfferFirstWithNull();
    }

    private static void testOfferFirstWithNonNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.offerFirst("test");
        if (!result) {
            throw new AssertionError("offerFirst should return true when adding non-null element");
        }
        String first = deque.peekFirst();
        if (!"test".equals(first)) {
            throw new AssertionError("peekFirst should return the element just added");
        }
    }

    private static void testOfferFirstWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.offerFirst(null);
            throw new AssertionError("offerFirst should throw NullPointerException when adding null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
