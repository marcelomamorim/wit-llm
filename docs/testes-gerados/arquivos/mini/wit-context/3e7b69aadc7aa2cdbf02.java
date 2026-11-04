/*
 * @test
 * @summary Test LinkedBlockingDeque.removeLast() behavior including NoSuchElementException
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test removeLast() throws NoSuchElementException when empty
        boolean threw = false;
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when removing last from empty deque");
        }

        // Test removeLast() returns last element when deque is not empty
        deque.addFirst("first");
        deque.addLast("last");
        String removed = deque.removeLast();
        if (!"last".equals(removed)) {
            throw new AssertionError("removeLast() did not return the last element");
        }

        // After removal, deque should contain only "first"
        String remaining = deque.removeLast();
        if (!"first".equals(remaining)) {
            throw new AssertionError("Deque state incorrect after removeLast()");
        }

        // Now deque is empty again, removeLast() should throw again
        threw = false;
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException on second removeLast() from empty deque");
        }
    }
}
