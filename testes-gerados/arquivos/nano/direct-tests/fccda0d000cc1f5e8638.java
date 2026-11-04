/* @test    @summary Test push method of LinkedBlockingDeque for correct behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPushAddsElement();
        testPushWithNullElement();
    }

    static void testPushAddsElement() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.push(42);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after push");
        }
        Integer first = deque.peekFirst();
        if (first == null || first != 42) {
            throw new AssertionError("First element should be 42, but was: " + first);
        }
    }

    static void testPushWithNullElement() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.push(null);
            if (deque.isEmpty()) {
                throw new AssertionError("Deque should contain null after push(null)");
            }
            Integer first = deque.peekFirst();
            if (first != null) {
                throw new AssertionError("First element should be null, but was: " + first);
            }
        } catch (NullPointerException e) {
            // Expected if nulls are not allowed; if nulls are allowed, this block can be omitted.
        }
    }
}
