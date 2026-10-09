/*
 * @test    @summary Testa o método offerLast do LinkedBlockingDeque.
 * @run main LinkedBlockingDequeWitupTest_P5f1b908bc096
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.lang.NullPointerException;

public class LinkedBlockingDequeWitupTest_P5f1b908bc096 {

    public static void main(String[] args) {
        testOfferLastNull();
        testOfferLastNormal();
    }

    private static void testOfferLastNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null element.");
        }
    }

    private static void testOfferLastNormal() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "element";
        boolean result = deque.offerLast(element);
        if (!result) {
            throw new AssertionError("offerLast should return true for normal element.");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offer.");
        }
        String first = deque.peekFirst();
        if (first == null || !first.equals(element)) {
            throw new AssertionError("Deque first element should be the offered element.");
        }
    }
}
