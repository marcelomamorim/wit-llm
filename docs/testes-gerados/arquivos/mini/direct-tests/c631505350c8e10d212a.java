/*
 * @test
 * @summary Test LinkedBlockingDeque.peek() returns the first element or null if empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test peek on empty deque returns null
        if (deque.peek() != null) {
            throw new AssertionError("peek() on empty deque should return null");
        }

        // Add one element and test peek returns it without removal
        String element = "testElement";
        deque.add(element);
        String peeked = deque.peek();
        if (!element.equals(peeked)) {
            throw new AssertionError("peek() should return the first element added");
        }

        // Test peek does not remove the element
        String removed = deque.poll();
        if (!element.equals(removed)) {
            throw new AssertionError("poll() should return the same element as peek()");
        }

        // After removal, peek should return null again
        if (deque.peek() != null) {
            throw new AssertionError("peek() after removal should return null");
        }
    }
}
