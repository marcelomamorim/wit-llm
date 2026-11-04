/*
 * @test
 * @summary Test LinkedBlockingDeque.addFirst(E) behavior including capacity limit
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testAddFirstSucceeds();
        testAddFirstThrowsWhenFull();
    }

    static void testAddFirstSucceeds() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        deque.addFirst("one");
        deque.addFirst("two");
        // The deque should contain "two" at first and "one" last
        String first = deque.pollFirst();
        String second = deque.pollFirst();
        if (!"two".equals(first)) {
            throw new AssertionError("Expected first element 'two', got: " + first);
        }
        if (!"one".equals(second)) {
            throw new AssertionError("Expected second element 'one', got: " + second);
        }
    }

    static void testAddFirstThrowsWhenFull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.addFirst("only");
        try {
            deque.addFirst("overflow");
            throw new AssertionError("Expected IllegalStateException when adding to full deque");
        } catch (IllegalStateException ise) {
            // Expected exception
            String msg = ise.getMessage();
            if (msg == null || !msg.equals("Deque full")) {
                throw new AssertionError("IllegalStateException message mismatch: " + msg);
            }
        }
    }
}
