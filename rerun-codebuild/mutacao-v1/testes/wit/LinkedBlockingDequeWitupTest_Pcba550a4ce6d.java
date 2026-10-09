/* @test    @summary Teste do método offerLast() de LinkedBlockingDeque para verificar comportamento com entradas nulas e não nulas    @run main LinkedBlockingDequeWitupTest_Pcba550a4ce6d */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_Pcba550a4ce6d {

    public static void main(String[] args) {
        testOfferLastWithNull();
        testOfferLastWithValidElement();
    }

    private static void testOfferLastWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.offerLast(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown for null input");
        }
    }

    private static void testOfferLastWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        boolean result = deque.offerLast(element);
        if (!result) {
            throw new AssertionError("offerLast returned false for valid element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque is empty after offerLast with valid element");
        }
        if (!deque.peekLast().equals(element)) {
            throw new AssertionError("Last element in deque does not match the offered element");
        }
    }
}
