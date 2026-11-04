/*
 * @test
 * @summary Test LinkedBlockingDeque.element() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testElementOnNonEmptyDeque();
        testElementOnEmptyDequeThrows();
    }

    static void testElementOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        String element = deque.element();
        if (!"first".equals(element)) {
            throw new AssertionError("Expected 'first' but got: " + element);
        }
    }

    static void testElementOnEmptyDequeThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.element();
            throw new AssertionError("Expected NoSuchElementException when calling element() on empty deque");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
