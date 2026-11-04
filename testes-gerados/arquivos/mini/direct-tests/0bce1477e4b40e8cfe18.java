/*
 * @test
 * @summary Test LinkedBlockingDeque.offerFirst(E) for null and normal element
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test offerFirst with a normal element
        boolean offered = deque.offerFirst("test");
        if (!offered) {
            throw new AssertionError("offerFirst should return true when adding element");
        }

        // Test that the element is actually at the front
        String first = deque.peekFirst();
        if (!"test".equals(first)) {
            throw new AssertionError("peekFirst should return the element just added");
        }

        // Test offerFirst with null element throws NullPointerException
        try {
            deque.offerFirst(null);
            throw new AssertionError("offerFirst(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
