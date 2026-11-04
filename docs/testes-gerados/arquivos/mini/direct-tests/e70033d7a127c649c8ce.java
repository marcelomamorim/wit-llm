/*
 * @test
 * @summary Test LinkedBlockingDeque.peekLast() behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test peekLast on empty deque returns null
        if (deque.peekLast() != null) {
            throw new AssertionError("peekLast() on empty deque should return null");
        }

        // Add one element and test peekLast returns it
        String element = "test";
        deque.add(element);
        String peeked = deque.peekLast();
        if (!element.equals(peeked)) {
            throw new AssertionError("peekLast() should return the last element added");
        }

        // Add another element and test peekLast returns the new last element
        String element2 = "last";
        deque.add(element2);
        peeked = deque.peekLast();
        if (!element2.equals(peeked)) {
            throw new AssertionError("peekLast() should return the last element added");
        }

        // Remove last element and test peekLast returns previous element
        deque.removeLast();
        peeked = deque.peekLast();
        if (!element.equals(peeked)) {
            throw new AssertionError("peekLast() should return the last element after removal");
        }

        System.out.println("All tests passed.");
    }
}
