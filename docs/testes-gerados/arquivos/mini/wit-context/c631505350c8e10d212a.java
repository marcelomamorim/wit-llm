/*
 * @test
 * @summary Test LinkedBlockingDeque.peek() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test peek() on empty deque returns null
        if (deque.peek() != null) {
            throw new AssertionError("peek() on empty deque should return null");
        }

        // Test peek() on non-empty deque returns first element without removal
        deque.add("first");
        deque.add("second");
        String peeked = deque.peek();
        if (!"first".equals(peeked)) {
            throw new AssertionError("peek() should return the first element");
        }
        // Confirm element was not removed
        if (deque.size() != 2) {
            throw new AssertionError("peek() should not remove element");
        }

        // Test peek() after removing first element
        deque.removeFirst();
        if (!"second".equals(deque.peek())) {
            throw new AssertionError("peek() should return new first element after removal");
        }

        System.out.println("All tests passed.");
    }
}
