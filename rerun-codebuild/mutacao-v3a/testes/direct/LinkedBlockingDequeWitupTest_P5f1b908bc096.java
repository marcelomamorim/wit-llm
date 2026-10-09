/* @test    @summary Test offerLast method of LinkedBlockingDeque for basic functionality and null handling    @run main LinkedBlockingDequeWitupTest_P5f1b908bc096 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_P5f1b908bc096 {

    public static void main(String[] args) {
        testOfferLastWithElement();
        testOfferLastWithNull();
    }

    static void testOfferLastWithElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerLast("element");
        if (!result) {
            throw new AssertionError("offerLast should return true when adding a non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerLast with element");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("First element should be 'element', but was: " + head);
        }
    }

    static void testOfferLastWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException npe) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("offerLast(null) should throw NullPointerException");
        }
    }
}
