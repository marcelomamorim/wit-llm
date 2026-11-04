/* @test    @summary Test the element() method of LinkedBlockingDeque for correct behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testElementReturnsFirstElement();
        testElementOnEmptyDeque();
    }

    private static void testElementReturnsFirstElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        String result = deque.element();
        if (!"first".equals(result)) {
            throw new AssertionError("Expected 'first', but got: " + result);
        }
    }

    private static void testElementOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.element();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
