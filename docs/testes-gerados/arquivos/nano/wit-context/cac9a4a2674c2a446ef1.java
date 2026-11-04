/*
 * @test    @summary Test for ConcurrentLinkedDeque.next() method.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNextWithElement();
        testNextWithoutElement();
    }

    static void testNextWithElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element");
        String result = deque.next();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', got: " + result);
        }
    }

    static void testNextWithoutElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        }
    }
}
