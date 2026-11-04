/*
 * @test
 * @summary Test LinkedBlockingDeque.element() method behavior including exception path
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testElementOnNonEmptyDeque();
        testElementOnEmptyDeque();
    }

    private static void testElementOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        String element = deque.element();
        if (!"first".equals(element)) {
            throw new AssertionError("element() did not return the expected first element");
        }
    }

    private static void testElementOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.element();
            throw new AssertionError("element() did not throw NoSuchElementException on empty deque");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
