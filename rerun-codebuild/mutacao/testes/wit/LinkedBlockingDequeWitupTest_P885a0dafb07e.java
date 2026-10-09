/*
 * @test
 * @summary Testa o método offerFirst de LinkedBlockingDeque para comportamentos observáveis.
 * @run main LinkedBlockingDequeWitupTest_P885a0dafb07e
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_P885a0dafb07e {

    public static void main(String[] args) {
        testOfferFirstWithNull();
        testOfferFirstWithValidElement();
    }

    private static void testOfferFirstWithNull() {
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

    private static void testOfferFirstWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        boolean result = deque.offerFirst(element);
        if (!result) {
            throw new AssertionError("offerFirst should return true for valid element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerFirst with valid element");
        }
        String first = deque.peekFirst();
        if (!element.equals(first)) {
            throw new AssertionError("First element should be the offered element");
        }
    }
}
