/*
 * @test
 * @summary Test LinkedBlockingDeque.getLast() behavior including exception on empty deque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test that getLast throws NoSuchElementException when deque is empty
        try {
            deque.getLast();
            throw new AssertionError("Expected NoSuchElementException when calling getLast on empty deque");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }

        // Test that getLast returns the last element when deque is not empty
        deque.addLast("first");
        deque.addLast("second");
        String last = deque.getLast();
        if (!"second".equals(last)) {
            throw new AssertionError("getLast() returned '" + last + "' but expected 'second'");
        }

        // Test that getLast returns the only element if only one element present
        LinkedBlockingDeque<Integer> singleDeque = new LinkedBlockingDeque<>();
        singleDeque.add(42);
        Integer val = singleDeque.getLast();
        if (!Integer.valueOf(42).equals(val)) {
            throw new AssertionError("getLast() returned " + val + " but expected 42");
        }
    }
}
