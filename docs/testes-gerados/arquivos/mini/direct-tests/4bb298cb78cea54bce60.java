/*
 * @test
 * @summary Test LinkedBlockingDeque remove() method delegates to removeFirst()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveOnNonEmptyDeque();
        testRemoveOnEmptyDequeThrows();
    }

    static void testRemoveOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        String removed = deque.remove();
        if (!"first".equals(removed)) {
            throw new AssertionError("Expected 'first' but got: " + removed);
        }
        // After removal, first element should be 'second'
        String next = deque.peekFirst();
        if (!"second".equals(next)) {
            throw new AssertionError("Expected 'second' after removal but got: " + next);
        }
    }

    static void testRemoveOnEmptyDequeThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.remove();
            throw new AssertionError("Expected NoSuchElementException when removing from empty deque");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
