/*
 * @test
 * @summary Test for LinkedBlockingDeque.element() method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testElementReturnsFirstElement();
        testElementThrowsWhenEmpty();
    }

    private static void testElementReturnsFirstElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        String result = deque.element();
        if (!"first".equals(result)) {
            throw new AssertionError("Expected 'first', but got: " + result);
        }
    }

    private static void testElementThrowsWhenEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.element();
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
