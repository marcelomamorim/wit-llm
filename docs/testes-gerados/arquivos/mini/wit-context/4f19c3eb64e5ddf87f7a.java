/*
 * @test
 * @summary Test LinkedBlockingDeque.pollFirst() for correct behavior and locking
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // pollFirst on empty deque returns null
        if (deque.pollFirst() != null) {
            throw new AssertionError("pollFirst() on empty deque should return null");
        }

        // Add one element and pollFirst should return it
        String element = "test";
        deque.addFirst(element);
        String polled = deque.pollFirst();
        if (!element.equals(polled)) {
            throw new AssertionError("pollFirst() did not return the expected element");
        }

        // After pollFirst, deque should be empty again
        if (deque.pollFirst() != null) {
            throw new AssertionError("pollFirst() after removing element should return null");
        }

        // Add multiple elements and pollFirst returns them in FIFO order
        deque.addLast("one");
        deque.addLast("two");
        deque.addLast("three");

        String first = deque.pollFirst();
        if (!"one".equals(first)) {
            throw new AssertionError("pollFirst() expected 'one', got: " + first);
        }

        String second = deque.pollFirst();
        if (!"two".equals(second)) {
            throw new AssertionError("pollFirst() expected 'two', got: " + second);
        }

        String third = deque.pollFirst();
        if (!"three".equals(third)) {
            throw new AssertionError("pollFirst() expected 'three', got: " + third);
        }

        // Now empty again
        if (deque.pollFirst() != null) {
            throw new AssertionError("pollFirst() on empty deque after removals should return null");
        }
    }
}
