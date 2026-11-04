/*
 * @test    @summary Test ArrayDeque.offer() method for basic functionality.
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferWithNonNullElement();
        testOfferWithNullElement();
    }

    static void testOfferWithNonNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.offer("element");
        if (!result) {
            throw new AssertionError("offer() should return true when adding non-null element");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the element after offer()");
        }
        if (!deque.peekLast().equals("element")) {
            throw new AssertionError("Last element should be 'element' after offer()");
        }
    }

    static void testOfferWithNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.offer(null);
        if (result) {
            throw new AssertionError("offer() should return true when adding null element, but null is not allowed in ArrayDeque");
        }
        if (!deque.isEmpty()) {
            throw new AssertionError("Deque should be empty after attempting to offer null");
        }
    }
}
