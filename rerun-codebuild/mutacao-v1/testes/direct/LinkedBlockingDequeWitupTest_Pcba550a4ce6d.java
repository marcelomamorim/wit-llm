/* @test    @summary Test offerLast method of LinkedBlockingDeque for null and normal elements    @run main LinkedBlockingDequeWitupTest_Pcba550a4ce6d */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_Pcba550a4ce6d {

    public static void main(String[] args) {
        testOfferLastWithNull();
        testOfferLastWithElement();
    }

    static void testOfferLastWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }

    static void testOfferLastWithElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerLast("element");
        if (!result) {
            throw new AssertionError("offerLast should return true for non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerLast with element");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("First element should be 'element', but was: " + head);
        }
    }
}
