/*
 * @test
 * @summary Test LinkedBlockingDeque.peekFirst() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPeekFirstEmptyDeque();
        testPeekFirstNonEmptyDeque();
    }

    private static void testPeekFirstEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        // On empty deque, peekFirst() should return null
        if (deque.peekFirst() != null) {
            throw new AssertionError("peekFirst() on empty deque should return null");
        }
    }

    private static void testPeekFirstNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "testElement";
        deque.addFirst(element);
        // peekFirst() should return the first element without removing it
        String peeked = deque.peekFirst();
        if (!element.equals(peeked)) {
            throw new AssertionError("peekFirst() did not return the expected element");
        }
        // The element should still be present after peekFirst()
        String removed = deque.removeFirst();
        if (!element.equals(removed)) {
            throw new AssertionError("Element removed is not the same as peeked element");
        }
    }
}
