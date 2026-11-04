/*
 * @test
 * @summary Test LinkedBlockingDeque.offer(E) delegates to offerLast(E) and behaves correctly
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test offer with a non-null element returns true
        boolean offered = deque.offer("test");
        if (!offered) {
            throw new AssertionError("offer should return true when adding element");
        }

        // Test that the element is actually added at the end
        String last = deque.pollLast();
        if (!"test".equals(last)) {
            throw new AssertionError("Element added by offer should be at the end");
        }

        // Test offer with null element throws NullPointerException
        boolean npeThrown = false;
        try {
            deque.offer(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("offer(null) should throw NullPointerException");
        }
    }
}
