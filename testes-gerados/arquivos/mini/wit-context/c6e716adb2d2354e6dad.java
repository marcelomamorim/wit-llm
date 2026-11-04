/*
 * @test
 * @summary Test LinkedBlockingDeque.pop() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testPopOnEmptyDeque();
        testPopOnNonEmptyDeque();
    }

    private static void testPopOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.pop();
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testPopOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        String popped = deque.pop();
        if (!"first".equals(popped)) {
            throw new RuntimeException("pop() did not return the first element");
        }
        // After pop, first element should be "second"
        String next = deque.peekFirst();
        if (!"second".equals(next)) {
            throw new RuntimeException("Deque state incorrect after pop");
        }
    }
}
