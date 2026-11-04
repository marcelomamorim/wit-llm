/* @test    @summary Test offerLast method of ArrayDeque for adding elements and returning true    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferLastWithNonNullElement();
        testOfferLastWithNullElement();
    }

    private static void testOfferLastWithNonNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.offerLast("element");
        if (!result) {
            throw new AssertionError("offerLast should return true when adding a non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the element after offerLast");
        }
        String first = deque.peekFirst();
        if (!"element".equals(first)) {
            throw new AssertionError("First element should be 'element', but was: " + first);
        }
    }

    private static void testOfferLastWithNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            boolean result = deque.offerLast(null);
            if (result) {
                throw new AssertionError("offerLast should not accept null elements and should throw NullPointerException");
            }
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
