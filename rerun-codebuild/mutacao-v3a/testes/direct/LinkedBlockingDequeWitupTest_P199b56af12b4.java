/*
 * @test
 * @summary Test offerFirst method of LinkedBlockingDeque for basic functionality
 * @run main LinkedBlockingDequeWitupTest_P199b56af12b4
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_P199b56af12b4 {

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
            throw new AssertionError("offerFirst(null) should throw NullPointerException");
        }
    }

    static void testOfferFirstWithValidElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerFirst("element");
        if (!result) {
            throw new AssertionError("offerFirst should return true for non-null element");
        }
        if (!"element".equals(deque.peekFirst())) {
            throw new AssertionError("Element not inserted at first position");
        }
    }
}
