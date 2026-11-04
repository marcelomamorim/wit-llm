/*
 * @test
 * @summary Test LinkedBlockingDeque removeFirst method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveFirstOnEmptyDequeThrows();
        testRemoveFirstReturnsFirstElement();
    }

    static void testRemoveFirstOnEmptyDequeThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.removeFirst();
            throw new Error("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testRemoveFirstReturnsFirstElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("first");
        deque.addLast("second");
        String removed = deque.removeFirst();
        if (!"first".equals(removed)) {
            throw new Error("removeFirst did not return the first element");
        }
        // After removal, the next element should be "second"
        String next = deque.peekFirst();
        if (!"second".equals(next)) {
            throw new Error("After removeFirst, peekFirst did not return the expected element");
        }
    }
}
