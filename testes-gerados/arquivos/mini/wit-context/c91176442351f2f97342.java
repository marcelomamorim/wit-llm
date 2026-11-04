/*
 * @test
 * @summary Test LinkedBlockingDeque.removeFirst() behavior including exception on empty deque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test removeFirst throws NoSuchElementException when empty
        boolean threw = false;
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when removing from empty deque");
        }

        // Test removeFirst returns the first element when present
        deque.addLast("first");
        deque.addLast("second");
        String first = deque.removeFirst();
        if (!"first".equals(first)) {
            throw new AssertionError("removeFirst did not return the first element");
        }

        // After removal, the next removeFirst returns the next element
        String second = deque.removeFirst();
        if (!"second".equals(second)) {
            throw new AssertionError("removeFirst did not return the second element after first removal");
        }

        // Now deque is empty again, removeFirst should throw again
        threw = false;
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when removing from empty deque after removals");
        }
    }
}
