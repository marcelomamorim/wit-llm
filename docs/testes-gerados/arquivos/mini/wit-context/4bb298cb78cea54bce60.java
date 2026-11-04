/*
 * @test
 * @summary Test LinkedBlockingDeque.remove() method for expected behavior and exception
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testRemoveOnEmptyDeque();
        testRemoveOnNonEmptyDeque();
    }

    private static void testRemoveOnEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.remove();
            throw new AssertionError("Expected NoSuchElementException when removing from empty deque");
        } catch (NoSuchElementException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }

    private static void testRemoveOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        String removed = deque.remove();
        if (!"first".equals(removed)) {
            throw new AssertionError("Removed element mismatch: expected 'first', got '" + removed + "'");
        }
        if (!deque.isEmpty()) {
            throw new AssertionError("Deque should be empty after remove");
        }
    }
}
