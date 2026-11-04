/*
 * @test
 * @summary Tests for LinkedBlockingDeque.offer(E e) method, covering null and non-null inputs.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithNonNull();
    }

    public static void testOfferWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offer(null);
        if (result != true) {
            throw new AssertionError("Expected offer(null) to return true");
        }
        if (deque.peekLast() != null) {
            throw new AssertionError("Expected last element to be null after offer(null)");
        }
    }

    public static void testOfferWithNonNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        boolean result = deque.offer(element);
        if (result != true) {
            throw new AssertionError("Expected offer(element) to return true");
        }
        if (!element.equals(deque.peekLast())) {
            throw new AssertionError("Expected last element to be 'test' after offer('test')");
        }
    }
}
