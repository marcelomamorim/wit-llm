/* @test    @summary Test offerFirst method of LinkedBlockingDeque for null and normal elements    @run main LinkedBlockingDequeWitupTest_P4b65f21b0f65 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_P4b65f21b0f65 {

    public static void main(String[] args) {
        testOfferFirstWithNull();
        testOfferFirstWithElement();
    }

    static void testOfferFirstWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean threw = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }

    static void testOfferFirstWithElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerFirst("element");
        if (!result) {
            throw new AssertionError("offerFirst should return true for non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerFirst with element");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("Head element should be 'element', but was: " + head);
        }
    }
}
