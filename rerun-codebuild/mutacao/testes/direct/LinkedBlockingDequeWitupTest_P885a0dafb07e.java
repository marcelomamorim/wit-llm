/* @test    @summary Test offerFirst method of LinkedBlockingDeque    @run main LinkedBlockingDequeWitupTest_P885a0dafb07e */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest_P885a0dafb07e {

    public static void main(String[] args) {
        testOfferFirstWithNull();
        testOfferFirstWithValidElement();
    }

    static void testOfferFirstWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
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

    static void testOfferFirstWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerFirst("element");
        if (!result) {
            throw new AssertionError("offerFirst should return true for non-null element");
        }
        String head = deque.peekFirst();
        if (!"element".equals(head)) {
            throw new AssertionError("Head element should be 'element', but was: " + head);
        }
    }
}
