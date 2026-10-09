/*
 * @test    @summary Test offerLast method of LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest_Pce906a137da9
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest_Pce906a137da9 {

    public static void main(String[] args) {
        testOfferLastWithNonNullElement();
        testOfferLastWithNullElement();
    }

    static void testOfferLastWithNonNullElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerLast("element");
        if (!result) {
            throw new AssertionError("offerLast should return true for non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the element after offerLast");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("First element should be 'element'");
        }
    }

    static void testOfferLastWithNullElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("offerLast should throw NullPointerException for null element");
        }
    }
}
