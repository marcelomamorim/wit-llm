/*
 * @test
 * @summary Test LinkedBlockingDeque addLast method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testAddLastSucceeds();
        testAddLastThrowsWhenFull();
    }

    static void testAddLastSucceeds() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        deque.addLast("one");
        deque.addLast("two");
        // The deque should contain the elements in order
        String first = deque.pollFirst();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected 'one' but got " + first);
        }
        String second = deque.pollFirst();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected 'two' but got " + second);
        }
    }

    static void testAddLastThrowsWhenFull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.addLast("only");
        try {
            deque.addLast("overflow");
            throw new AssertionError("Expected IllegalStateException when adding to full deque");
        } catch (IllegalStateException ise) {
            // Expected exception
            String msg = ise.getMessage();
            if (msg == null || !msg.equals("Deque full")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
