/*
 * @test
 * @summary Test the next() method of ConcurrentLinkedDeque for normal and exceptional behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNextWithElement();
        testNextWithNullElement();
    }

    static void testNextWithElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element");
        String result = deque.next();
        assert result.equals("element") : "Expected 'element', got " + result;
    }

    static void testNextWithNullElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        // Simulate internal state where nextItem is null
        // Since next() accesses nextItem, we need to set it to null
        // but nextItem is private; instead, we test behavior when deque is empty
        try {
            deque.next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
