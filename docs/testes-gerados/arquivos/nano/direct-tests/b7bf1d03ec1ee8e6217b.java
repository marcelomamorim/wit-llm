/* @test    @summary Test offer method of LinkedBlockingDeque for normal and null elements    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testOfferWithElement();
        testOfferWithNull();
    }

    static void testOfferWithElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offer("element");
        if (!result) {
            throw new AssertionError("offer should return true for non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the offered element");
        }
        if (!deque.peekLast().equals("element")) {
            throw new AssertionError("Last element should be 'element'");
        }
    }

    static void testOfferWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.offer(null);
            throw new AssertionError("offer should throw NullPointerException for null element");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
