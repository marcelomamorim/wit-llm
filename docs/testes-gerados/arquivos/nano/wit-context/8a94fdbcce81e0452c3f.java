/* @test    @summary Testando o método offerLast() de LinkedBlockingDeque com entradas nulas e não nulas    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testOfferLastWithNull();
        testOfferLastWithValidElement();
    }

    private static void testOfferLastWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null element");
        }
    }

    private static void testOfferLastWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        boolean result = deque.offerLast(element);
        if (!result) {
            throw new AssertionError("offerLast should return true for valid element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after offerLast with valid element");
        }
        String retrieved = deque.peekLast();
        if (!element.equals(retrieved)) {
            throw new AssertionError("Element retrieved does not match element offered");
        }
    }
}
