/*
 * @test
 * @summary Test LinkedBlockingDeque.removeLast() behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testRemoveLastOnEmptyDeque();
        testRemoveLastOnNonEmptyDeque();
    }

    private static void testRemoveLastOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.removeLast();
            throw new Error("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    private static void testRemoveLastOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        String last = deque.removeLast();
        if (!"b".equals(last)) {
            throw new Error("Expected 'b' but got: " + last);
        }
        // After removal, last element should be "a"
        String newLast = deque.removeLast();
        if (!"a".equals(newLast)) {
            throw new Error("Expected 'a' but got: " + newLast);
        }
        // Now deque is empty, removeLast should throw
        try {
            deque.removeLast();
            throw new Error("Expected NoSuchElementException not thrown after removing all elements");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
