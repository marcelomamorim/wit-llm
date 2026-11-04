/*
 * @test
 * @summary Test java.util.ArrayDeque.offerFirst(E) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();

        // Test offerFirst returns true and element is added at front
        boolean offered = deque.offerFirst("first");
        if (!offered) {
            throw new AssertionError("offerFirst should return true");
        }
        String peeked = deque.peekFirst();
        if (!"first".equals(peeked)) {
            throw new AssertionError("Element added by offerFirst not at front");
        }

        // Add another element and check order
        deque.offerFirst("second");
        String first = deque.pollFirst();
        if (!"second".equals(first)) {
            throw new AssertionError("Second element should be at front after offerFirst");
        }
        String second = deque.pollFirst();
        if (!"first".equals(second)) {
            throw new AssertionError("First element should be second after second offerFirst");
        }

        // Test offerFirst with null throws NullPointerException
        try {
            deque.offerFirst(null);
            throw new AssertionError("offerFirst(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
