/*
 * @test
 * @summary Test deterministic behavior of java.util.ArrayDeque.offer(E e) including null rejection
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testOfferWithNonNull();
        testOfferWithNull();
    }

    private static void testOfferWithNonNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.offer("test");
        if (!result) {
            throw new AssertionError("offer should return true when adding non-null element");
        }
        if (!"test".equals(deque.peekLast())) {
            throw new AssertionError("Last element should be the one just offered");
        }
    }

    private static void testOfferWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.offer(null);
            throw new AssertionError("offer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
