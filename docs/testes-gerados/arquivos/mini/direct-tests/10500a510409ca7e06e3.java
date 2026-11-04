/*
 * @test
 * @summary Test java.util.ArrayDeque.offerLast(E) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();

        // Test offerLast returns true and element is added
        boolean offered = deque.offerLast("test");
        if (!offered) {
            throw new AssertionError("offerLast should return true");
        }
        String last = deque.peekLast();
        if (!"test".equals(last)) {
            throw new AssertionError("Last element should be 'test', but was: " + last);
        }

        // Test offerLast with multiple elements
        deque.offerLast("second");
        String last2 = deque.peekLast();
        if (!"second".equals(last2)) {
            throw new AssertionError("Last element should be 'second', but was: " + last2);
        }

        // Test offerLast with null element throws NullPointerException
        boolean npeThrown = false;
        try {
            deque.offerLast(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("offerLast(null) should throw NullPointerException");
        }
    }
}
