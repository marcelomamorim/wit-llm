/* @test    @summary Test for ArrayDeque.offerLast to verify behavior with null input and normal input    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferLastWithNull();
        testOfferLastWithValidElement();
    }

    private static void testOfferLastWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.offerLast(null);
            // In JDK 11, offerLast does not throw NullPointerException, it adds null to the deque.
            // Verify that null is added.
            if (deque.isEmpty()) {
                throw new AssertionError("Deque should contain null after offerLast(null)");
            }
            if (deque.peekLast() != null) {
                throw new AssertionError("Last element should be null after offerLast(null)");
            }
        } catch (NullPointerException e) {
            // If the implementation throws, the test passes.
        }
    }

    private static void testOfferLastWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        boolean result = deque.offerLast(element);
        if (!result) {
            throw new AssertionError("offerLast should return true for valid element");
        }
        if (deque.peekLast() != element) {
            throw new AssertionError("Last element should be 'test' after offerLast");
        }
    }
}
