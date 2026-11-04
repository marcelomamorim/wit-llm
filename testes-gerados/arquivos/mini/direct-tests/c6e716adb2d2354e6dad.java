/*
 * @test
 * @summary Test LinkedBlockingDeque.pop() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPopOnNonEmptyDeque();
        testPopOnEmptyDequeThrows();
    }

    static void testPopOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");

        String popped = deque.pop();
        if (!"first".equals(popped)) {
            throw new AssertionError("Expected 'first' but got: " + popped);
        }

        // After pop, first element should be removed
        String next = deque.peekFirst();
        if (!"second".equals(next)) {
            throw new AssertionError("Expected 'second' after pop but got: " + next);
        }
    }

    static void testPopOnEmptyDequeThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.pop();
            throw new AssertionError("Expected NoSuchElementException when popping from empty deque");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
