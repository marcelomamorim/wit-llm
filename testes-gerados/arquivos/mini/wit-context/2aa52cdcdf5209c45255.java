/*
 * @test
 * @summary Test LinkedBlockingDeque.getFirst() behavior including exception on empty deque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test getFirst throws NoSuchElementException when empty
        try {
            deque.getFirst();
            throw new Error("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }

        // Test getFirst returns the first element when present
        deque.addFirst("first");
        String first = deque.getFirst();
        if (!"first".equals(first)) {
            throw new Error("getFirst() returned wrong element: " + first);
        }

        // Test getFirst does not remove the element
        String peeked = deque.peekFirst();
        if (!"first".equals(peeked)) {
            throw new Error("peekFirst() returned wrong element after getFirst(): " + peeked);
        }

        // Add another element and test getFirst still returns the first
        deque.addLast("second");
        first = deque.getFirst();
        if (!"first".equals(first)) {
            throw new Error("getFirst() returned wrong element after adding second: " + first);
        }

        System.out.println("Test passed");
    }
}
