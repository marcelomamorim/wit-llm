/* @test    @summary Testa o método offerFirst() de LinkedBlockingDeque para comportamentos observáveis e exceções.    @run main LinkedBlockingDequeWitupTest_P4b65f21b0f65 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest_P4b65f21b0f65 {

    public static void main(String[] args) {
        testOfferFirstNull();
        testOfferFirstNormal();
    }

    private static void testOfferFirstNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }

    private static void testOfferFirstNormal() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerFirst("element");
        if (!result) {
            throw new AssertionError("offerFirst should return true for normal insertion");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerFirst");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("Head element mismatch: expected 'element', got " + head);
        }
    }
}
